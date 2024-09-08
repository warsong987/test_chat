package ru.ivan.eremin.testchat.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ServiceValidateInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(
            request
                .newBuilder()
                .build()


        )

        while(!response.isSuccessful && response.code == 422) {
            response.body?.toString()
        }

        return response
    }
}