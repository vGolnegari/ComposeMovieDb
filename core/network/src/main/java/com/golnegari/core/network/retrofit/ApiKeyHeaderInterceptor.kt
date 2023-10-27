package com.golnegari.core.network.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyHeaderInterceptor constructor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
        val httpUrl = newRequest.url
        val newUrl = httpUrl.newBuilder().addQueryParameter("api_key", apiKey).build()
        return chain.proceed(newRequest.newBuilder().url(newUrl).build())

    }
}