package com.golnegari.core.network.base


import com.golnegari.core.network.util.NetworkConnectionUtil
import retrofit2.HttpException
import java.io.IOException

sealed class ApiResult<out T : BaseJson> {

    class Success<out T : BaseJson> (val data: T) : ApiResult<T>()

    class Error(val errorType : ErrorType) : ApiResult<Nothing>()

}

enum class ErrorType{
    NO_INTERNET_CONNECTION , GENERIC_NETWORK_ERROR,Unknown,
}

suspend fun <T : BaseJson> (suspend () -> T).getApiResult(
    networkConnectionUtil: NetworkConnectionUtil,
): ApiResult<T> =
    try {
        if (networkConnectionUtil.isNetworkConnected()) {
            val responseJson = this.invoke()
            ApiResult.Success(responseJson)
        } else {
            throw NetworkConnectionException()
        }
    } catch (throwable: Throwable) {
        when (throwable) {

            is IOException, is HttpException ->
                ApiResult.Error(errorType = ErrorType.GENERIC_NETWORK_ERROR)

            is NetworkConnectionException ->
                ApiResult.Error(errorType = ErrorType.NO_INTERNET_CONNECTION)

            else -> {
                ApiResult.Error(errorType = ErrorType.Unknown)
            }
        }
    }