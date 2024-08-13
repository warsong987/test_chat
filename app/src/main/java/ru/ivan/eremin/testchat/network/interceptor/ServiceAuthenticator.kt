package ru.ivan.eremin.testchat.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.ivan.eremin.testchat.network.extension.executeRequest
import javax.inject.Inject

class ServiceAuthenticator @Inject constructor() :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        var response = chain.executeRequest(request)

        return response
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}