package ru.ivan.eremin.chat.network.mapping

object ExceptionMapper {
    suspend fun Throwable.mapToAppException(): Throwable {
        return Throwable()
    }
}