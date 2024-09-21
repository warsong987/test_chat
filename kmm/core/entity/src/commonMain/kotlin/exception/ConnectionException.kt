package exception

import http.HttpRequest

open class ConnectionException(
    open val request: HttpRequest,
    cause: Throwable? = null
) : AppException(cause = cause) {
    override fun toString() = "ConnectionException(url=${request.url}, cause=$cause)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as ConnectionException
        if (request != other.request) return false
        return cause == other.cause
    }

    override fun hashCode(): Int {
        var result = request.hashCode()
        result = 31 * result + cause.hashCode()
        return result
    }

    override val message: String?
        get() = cause?.message
}