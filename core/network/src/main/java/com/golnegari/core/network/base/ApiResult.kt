package com.golnegari.core.network.base


import com.golnegari.core.network.util.NetworkConnectionUtil
import retrofit2.HttpException
import java.io.IOException

sealed class ApiResult<out T : BaseJson> {

    class Success<out T : BaseJson> (val data: T) : ApiResult<T>()

    object NetworkError : ApiResult<Nothing>()

    object LocalNetworkError : ApiResult<Nothing>()

    object NetworkUnknownError : ApiResult<Nothing>()

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
                ApiResult.NetworkError

            is NetworkConnectionException ->
                ApiResult.LocalNetworkError

            else -> {
                ApiResult.NetworkUnknownError
            }
        }
    }