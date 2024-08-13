package ru.ivan.eremin.testchat.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.ivan.eremin.testchat.network.timeout.ConnectTimeout
import ru.ivan.eremin.testchat.network.timeout.ReadTimeout
import ru.ivan.eremin.testchat.network.timeout.WriteTimeout
import ru.ivan.eremin.testchat.network.timeout.getAnnotation
import javax.inject.Inject

internal class TimeoutInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val connectionTimeout = request.getAnnotation<ConnectTimeout>()
        val readTimeout = request.getAnnotation<ReadTimeout>()
        val writeTimeout = request.getAnnotation<WriteTimeout>()
        var resultChain = connectionTimeout?.let { chain.withConnectTimeout(it.value, it.unit) } ?: chain
        resultChain = readTimeout?.let { resultChain.withReadTimeout(it.value, it.unit) } ?: resultChain
        resultChain = writeTimeout?.let { resultChain.withWriteTimeout(it.value, it.unit) } ?: resultChain
        return resultChain.proceed(request)
    }
}
