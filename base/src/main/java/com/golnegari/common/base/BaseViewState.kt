package com.golnegari.common.base

import kotlinx.coroutines.flow.Flow

interface BaseViewState {
    var isLoading: Boolean
    var errorType: ErrorType
    var errorMessage: String?
    var isExtraLoading: Boolean

    var retryFlow: Flow<UiAction>?
    fun baseState(): BaseViewState
    fun isContentVisible() = isLoading.not() && errorType == ErrorType.NOTHING
}


enum class ErrorType {
    SHOW_MESSAGE, SHOW_RETRY, LOCK, LOGOUT, NOTHING
}
