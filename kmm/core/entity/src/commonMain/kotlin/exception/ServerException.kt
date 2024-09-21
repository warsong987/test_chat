package exception

import http.HttpRequest
import http.HttpResponse

open class ServerException(
    override val message: String,
    val code: String? = null,
    val request: HttpRequest? = null,
    val response: HttpResponse? = null,
    override val cause: Throwable? = null
) : AppException(message, cause) {

    val url
        get() = request?.url
    val httpCode
        get() = response?.code

    override fun toString(): String {
        return "ServerException(code=$code, message=$message, url=$url, httpCode=$httpCode)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as ServerException
        if (code != other.code) return false
        if (message != other.message) return false
        if (request != other.request) return false
        if (response != other.response) return false
        return cause == other.cause
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + (this::class.hashCode())
        result = 31 * result + (code?.hashCode() ?: 0)
        result = 31 * result + (request?.hashCode() ?: 0)
        result = 31 * result + (response?.hashCode() ?: 0)
        result = 31 * result + (cause?.hashCode() ?: 0)
        return result
    }
}
