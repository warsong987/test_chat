package http

import exception.ConnectionException

open class TimeoutException(
    override val request: HttpRequest,
    cause: Throwable? = null
) : ConnectionException(request, cause = cause) {
    override fun toString(): String {
        return "TimeoutException(url=${request.url}, cause=$cause)"
    }

    override fun hashCode(): Int {
        var result = request.hashCode()
        result = 31 * result + cause.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        if (!super.equals(other)) return false

        other as TimeoutException

        return request == other.request
    }
}