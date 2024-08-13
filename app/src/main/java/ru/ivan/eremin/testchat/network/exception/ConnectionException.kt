package ru.ivan.eremin.testchat.network.exception

import android.net.Uri
import java.io.IOException

private fun Throwable?.mapCause(uri: Uri): Throwable {
    val cause = this
    var exception: Throwable? = this
    while (exception?.cause != null) {
        exception = exception.cause
    }
    val newException = ConnectionExceptionCause(cause?.message).apply {
        stackTrace = listOf(
            StackTraceElement(
                ConnectionExceptionCause::class.qualifiedName,
                cause?.stackTrace?.firstOrNull()?.methodName,
                uri.host,
                0
            )
        ).toTypedArray()
    }
    try {
        exception?.initCause(newException)
    } catch (_: Exception) {
        // no-op
    }
    return this ?: newException
}

open class ConnectionException(
    val url: Uri,
    cause: Throwable? = null
) : AppException(cause = cause.mapCause(url)) {
    override fun toString(): String {
        return "ConnectionException(url=$url, cause=$cause)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as ConnectionException
        if (url != other.url) return false
        if (cause != other.cause) return false
        return true
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + cause.hashCode()
        return result
    }
}

private data class ConnectionExceptionCause(override val message: String?) : IOException(message)