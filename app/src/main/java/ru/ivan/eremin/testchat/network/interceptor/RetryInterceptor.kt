package ru.ivan.eremin.testchat.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.ivan.eremin.testchat.network.constant.HEADER_ATTEMPT_COUNT
import ru.ivan.eremin.testchat.network.extension.executeRequest
import javax.inject.Inject

internal class RetryInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.executeRequest(
            request
                .newBuilder()
                .build()
        )

        var numberAttempts: Long = 1
        while (!response.isSuccessful &&
            response.code == 423 &&
            numberAttempts < MAX_ATTEMPTS_COUNT
        ) {
            try {
                Thread.sleep((numberAttempts * STEP_TIME))
            } catch (e: Exception) {
            }
            response.close()
            response = chain.executeRequest(
                request
                    .newBuilder()
                    .header(HEADER_ATTEMPT_COUNT, numberAttempts.toString())
                    .build()
            )
            numberAttempts++
        }
        return response
    }

    companion object {
        private const val MAX_ATTEMPTS_COUNT = 4
        private const val STEP_TIME = 1500L
    }
}
