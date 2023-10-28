package com.golnegari.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.golnegari.core.domain.base.BaseDomainModel
import com.golnegari.core.domain.base.DataResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

abstract class BaseViewModel<M : UiModel, S : UiState<M>, A : UiAction> constructor(initState: S) :
    ViewModel() {

    private val _uiStateFlow: MutableStateFlow<S> by lazy {
        MutableStateFlow(initState)
    }
    val uiStateFlow: StateFlow<S> = _uiStateFlow
    private val actionFlow: MutableSharedFlow<A> = MutableSharedFlow()
    private val _singleEventFlow = Channel<S>()
    val singleEventFlow = _singleEventFlow.receiveAsFlow()

    var uiState by Delegates.observable(initState) { _, _, newViewState ->
        viewModelScope.launch {
            _uiStateFlow.emit(newViewState)
        }
    }
        private set

    init {
        viewModelScope.launch {
            actionFlow.collect { action ->
                reduceState(action)
            }
        }
    }

    abstract fun reduceState(action: A)

    fun sendAction(action: A) {
        viewModelScope.launch {
            actionFlow.emit(action)
        }
    }

    fun submitState(state: S) {
        viewModelScope.launch {
            if (state is UiState.Navigation) {
                _singleEventFlow.send(state)
            } else {
                uiState = state
            }
        }
    }

    fun <D : BaseDomainModel, A : UiAction> collect(
        dataResultFlow: Flow<DataResult<D>>,
        retryAction: A,
        onError: ((DataResult.Error<D>) -> S?)? = null,
        onExtraLoading: (() -> S?)? = null,
        successMapper: (DataResult.Success<D>) -> S?,
    ): Job {
        return viewModelScope.launch {
            dataResultFlow.collect { dataResult ->
                val uiState = when (dataResult) {
                    is DataResult.Error -> {
                        val errorUiState = dataResult.getUiState(
                            retryAction = retryAction,
                            uiModel = uiState.uiModel,
                        )
                        onError?.invoke(dataResult) ?: errorUiState
                    }
                    is DataResult.Loading -> {
                        if (dataResult.isCommon) {
                            UiState.Loading(uiState.uiModel, isCommon = true)
                        } else {
                            onExtraLoading?.invoke() ?: UiState.Loading(
                                uiState.uiModel,
                                isCommon = false
                            )
                        }
                    }
                    is DataResult.Success -> {
                        successMapper(dataResult)
                    }
                }
                val event = uiState as? S
                event?.let {
                    submitState(event)
                }
            }
        }
    }
}
