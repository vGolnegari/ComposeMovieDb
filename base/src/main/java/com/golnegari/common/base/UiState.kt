package com.golnegari.common.base

import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.base.DataResultErrorType
import java.util.*

sealed class UiState<out UiModel : Any>(open val uiModel: UiModel?) {

    data class Loading<out UiModel : Any>(
        override val uiModel: UiModel? = null,
        val isCommon: Boolean = true
    ) : UiState<UiModel>(uiModel = uiModel)

    data class Success<out UiModel : Any>(override val uiModel: UiModel?) :
        UiState<UiModel>(uiModel = uiModel)

    data class ErrorMessage<out UiModel : Any, A : UiAction>(
        override val uiModel: UiModel?,
        val message: String,
        val retriable: Boolean = false,
        val retryAction: A? = null,
        val messageId: String = UUID.randomUUID().toString()
    ) : UiState<UiModel>(uiModel = uiModel)

    data class Navigation(
        val route: String,
        val popUpTo: String? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : UiState<Nothing>(uiModel = null)

}


fun <A : UiAction> DataResult.Error<*>.getUiState(
    retryAction: A? = null,
    uiModel: UiModel? = null,
): UiState<UiModel> {
    return when (type) {
        DataResultErrorType.NETWORK_GENERIC_ERROR -> {
            UiState.ErrorMessage(
                uiModel = uiModel,
                message = "Bad Response",
                retryAction = retryAction as A,
            )
        }

        DataResultErrorType.INTERNET_CONNECTION_FAILED -> {
            UiState.ErrorMessage(
                uiModel = uiModel,
                message = "Not Internet Connection",
                retryAction = retryAction as A,
            )
        }
    }
}
