package com.golnegari.common.base.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.navigation.NavHostController
import com.golnegari.common.base.UiAction
import com.golnegari.common.base.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun <T : Any, A : UiAction> CommonScreen(
    navController: NavHostController,
    uiStateState: State<UiState<T>>,
    singleEventFlow: Flow<UiState<T>>,
    sendAction: ((retryAction: A) -> Unit)? = null,
    reset: ((data: T?) -> Unit)? = null,
    content: @Composable (data: T?, snackBarHostState: SnackbarHostState) -> Unit,
) {
    val hostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        singleEventFlow.collectLatest {
            if (it is UiState.Navigation) {
                navController.enableOnBackPressed(true)
                navController.navigate(it.route) {
                    it.popUpTo?.let { popUpToRoute ->
                        popUpTo(popUpToRoute) {
                            inclusive = it.inclusive
                        }
                    }
                    launchSingleTop = it.singleTop
                }

            }
        }
    }

    ConstraintLayout {
        val (contentId, loadingId) = createRefs()
        Box(
            modifier = Modifier.Companion
                .constrainAs(contentId) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            content(
                data = uiStateState.value.uiModel,
                snackBarHostState = hostState,
            )
        }
        val value = uiStateState.value
        if (value is UiState.Loading) {
            if (value.isCommon) {
                LoadingBox(loadingId = loadingId)
            }
        } else if (value is UiState.ErrorMessage<*, *>) {
            val snackBarMessage =
                value.message.toIntOrNull()?.let { stringResource(it) } ?: value.message
            val actionLabel = if (value.retriable) {
                "Try Again"
            } else {
                null
            }
            val actionPerformed =
                { value.retryAction?.let { action -> sendAction?.invoke(action as A) } }
            val actionDismissed = {}
            LaunchedEffect(uiStateState.value) {
                coroutineScope.launch {
                    showSnackBar(
                        hostState = hostState,
                        snackBarMessage = snackBarMessage,
                        actionLabel = actionLabel,
                        actionPerformed = actionPerformed,
                        actionDismissed = actionDismissed,
                    )
                }
            }
        }
    }
}


private suspend fun showSnackBar(
    hostState: SnackbarHostState,
    snackBarMessage: String,
    actionLabel: String?,
    actionPerformed: () -> Unit?,
    actionDismissed: () -> Unit,
) {
    when (hostState.showSnackbar(
        message = snackBarMessage,
        actionLabel = actionLabel,
    )) {
        SnackbarResult.ActionPerformed -> {
            actionPerformed()
        }

        SnackbarResult.Dismissed -> {
            actionDismissed()
        }
    }
}

@Composable
private fun ConstraintLayoutScope.LoadingBox(
    loadingId: ConstrainedLayoutReference,
) {
    Box(
        Modifier
            .constrainAs(loadingId) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
