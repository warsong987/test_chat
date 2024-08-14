package ru.ivan.eremin.testchat.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.ivan.eremin.testchat.app.config.AppConfiguration
import ru.ivan.eremin.testchat.network.constant.HEADER_APP_VERSION
import ru.ivan.eremin.testchat.network.constant.HEADER_USER_AGENT
import javax.inject.Inject

internal class DeviceInfoInterceptor @Inject constructor(
    private val appConfiguration: AppConfiguration
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header(HEADER_USER_AGENT, appConfiguration.userAgent)
            .header(HEADER_APP_VERSION, appConfiguration.versionName)
            .build()
        return chain.proceed(request)
    }
}
