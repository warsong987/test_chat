package ru.ivan.eremin.chat.entity.exception

import ru.ivan.eremin.chat.entity.http.HttpRequest

open class SerializationException(
    override val cause: Throwable,
    override val message: String = cause.message.orEmpty(),
    val request: HttpRequest? = null,
) : AppException(message, cause) {

    val url
        get() = request?.url

    override fun toString(): String {
        return "SerializationException(url=$url, message=$message)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as SerializationException
        if (message != other.message) return false
        if (request != other.request) return false
        return cause == other.cause
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + (request?.hashCode() ?: 0)
        result = 31 * result + (cause.hashCode())
        return result
    }
}