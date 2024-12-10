package com.panostob.movieflix.util.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class MoviesApiInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        request.addHeader(MOVIES_AUTHORIZATION_HEADER, "Bearer: $MOVIES_AUTHORIZATION_TOKEN")

        return chain.proceed(request.build())
    }

    companion object {
        private const val MOVIES_AUTHORIZATION_HEADER = "Authorization"
        private const val HEADER_USER_AGENT = "User-Agent"
        private const val MOVIES_AUTHORIZATION_TOKEN = "f82912e5c27b184e90326831b51734bc"
    }
}