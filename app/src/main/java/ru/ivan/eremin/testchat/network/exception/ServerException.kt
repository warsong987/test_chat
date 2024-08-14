package ru.ivan.eremin.testchat.network.exception

import android.net.Uri
import java.io.IOException


private const val FILTERED_VALUE = "<*filtered*>"
private val IDS = Regex("\\d{2,}")
private const val INTERACTIONS = "interactions/"
private fun mapCause(uri: Uri?, type: String?, message: String?, httpCode: Int?): Throwable {
    val newException = ServerExceptionCause("${type ?: ""} ${message ?: ""}").apply {
        stackTrace = listOf(
            StackTraceElement(
                ServerExceptionCause::class.qualifiedName,
                "",
                uri?.host + uri?.path?.replace(IDS, FILTERED_VALUE)?.replaceAfter(INTERACTIONS, FILTERED_VALUE),
                httpCode ?: 0
            )
        ).toTypedArray()
    }
    return newException
}

open class ServerException(
    override val message: String,
    val type: String? = null,
    val url: Uri? = null,
    val httpCode: Int? = null
) : AppException(message, mapCause(url, type, message, httpCode)) {

    override fun toString(): String {
        return "ServerException(code=$type, message=$message, url=$url, httpCode=$httpCode)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as ServerException
        if (type != other.type) return false
        if (message != other.message) return false
        if (url != other.url) return false
        if (httpCode != other.httpCode) return false
        return true
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (httpCode ?: 0)
        return result
    }
}

private data class ServerExceptionCause(override val message: String?) : IOException(message)
