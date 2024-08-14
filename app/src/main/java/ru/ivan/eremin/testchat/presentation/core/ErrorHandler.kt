package ru.ivan.eremin.testchat.presentation.core

import ru.ivan.eremin.testchat.network.exception.ConnectionException
import ru.ivan.eremin.testchat.network.exception.LogOutException
import ru.ivan.eremin.testchat.network.exception.ServerException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandler {
    fun handle(
        error: Throwable,
        connection: (() -> Unit)? = null,
        server: ((text: String) -> Unit)? = null,
        unknown: (() -> Unit)? = null,
        logOut: (() -> Unit)
    ) {
        when {
            error.isCause() -> {
                handle(error.cause!!, connection, server, unknown, logOut)
            }

            error.isServer() -> {
                server?.invoke(error.message ?: "")
            }

            error.isNetwork() -> {
                connection?.invoke()
            }

            error.isLogOut() -> {
                logOut.invoke()
            }

            else -> {
                unknown?.invoke()
            }
        }
    }

    private fun Throwable.isCause() = this is RuntimeException && this.cause != null

    private fun Throwable.isNetwork() = this is UnknownHostException ||
            this is SocketException ||
            this is SocketTimeoutException ||
            this is ConnectionException

    private fun Throwable.isServer() =
        this is ServerException && this.message.isNotEmpty()

    private fun Throwable.isLogOut() = this is LogOutException

    fun Throwable.uiErrorHandle(): UiError {
        return when {
            isCause() -> {
                this.cause!!.uiErrorHandle()
            }

            isServer() -> {
                var errorCode = ""
                if (this is ServerException) {
                    type?.let { errorCode = it }
                }
                UiError.Server(message ?: "", errorCode)
            }

            isNetwork() -> {
                UiError.Network
            }

            isLogOut() -> {
                UiError.LogOut
            }

            else -> {
                UiError.Unknown
            }
        }
    }
}

fun Throwable.handle(
    connection: (() -> Unit)? = null,
    server: ((text: String) -> Unit)? = null,
    unknown: (() -> Unit)? = null,
    logOut: (() -> Unit)
) {
    ErrorHandler.handle(this, connection, server, unknown, logOut)
}
