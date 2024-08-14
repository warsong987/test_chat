package ru.ivan.eremin.testchat.network.extension

import android.net.Uri
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.ivan.eremin.testchat.network.exception.AppException
import ru.ivan.eremin.testchat.network.exception.ConnectionException

fun Interceptor.Chain.executeRequest(request: Request): Response = try {
    this.proceed(request)
} catch (e: AppException) {
    throw e
} catch (e: Exception) {
    throw ConnectionException(
        Uri.parse(request.url.toString()),
        e
    )
}
