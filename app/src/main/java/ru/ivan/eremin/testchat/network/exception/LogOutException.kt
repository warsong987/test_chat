package ru.ivan.eremin.testchat.network.exception

open class LogOutException : AppException() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
