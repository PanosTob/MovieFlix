package com.panostob.movieflix.util.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class MoviesApiInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        request.addHeader(MOVIES_API_KEY_HEADER, MOVIES_API_KEY_HEADER_VALUE)
        request.addHeader(MOVIES_AUTHORIZATION_HEADER, "Bearer $MOVIES_AUTHORIZATION_TOKEN")
        request.addHeader(MOVIES_HEADER_JSON_KEY, MOVIES_HEADER_JSON_VALUE)

        return chain.proceed(request.build())
    }

    companion object {
        private const val MOVIES_AUTHORIZATION_HEADER = "Authorization"
        private const val MOVIES_API_KEY_HEADER = "api_key"
        private const val MOVIES_API_KEY_HEADER_VALUE = "f48834436e80568db872fbeb2c08c6a7"
        private const val MOVIES_HEADER_JSON_KEY = "accept"
        private const val MOVIES_HEADER_JSON_VALUE = "application/json"
        private const val MOVIES_AUTHORIZATION_TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNDg4MzQ0MzZlODA1NjhkYjg3MmZiZWIyYzA4YzZhNyIsIm5iZiI6MTczMzg0NTEzMC42Nywic3ViIjoiNjc1ODYwOGFlZWRlYzIyZDhhZWMwYzk4Iiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.KFzLblwhKkAIS4gORw0yd4I4hc-9ppT5G58NCOZoBZo"
    }
}