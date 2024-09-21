package ru.ivan.eremin.feature.utils

import ru.ivan.eremin.feature.entity.UiError
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandler {

    private fun Throwable.isCause() = this is RuntimeException && this.cause != null


    private fun Throwable.isNetwork() = this is UnknownHostException ||
        this is SocketException ||
        this is SocketTimeoutException

    fun Throwable.uiErrorHandle(): UiError? {
        return null
    }
}
