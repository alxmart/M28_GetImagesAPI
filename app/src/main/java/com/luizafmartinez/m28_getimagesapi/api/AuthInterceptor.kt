package com.luizafmartinez.m28_getimagesapi.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()

        val request = requestBuilder.addHeader(
            "Authorization","Client-ID bbba47ebe7b6194"
        ).build()

        return chain.proceed(request)

    }
}