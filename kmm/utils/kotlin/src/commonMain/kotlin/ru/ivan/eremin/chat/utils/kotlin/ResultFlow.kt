package ru.ivan.eremin.chat.utils.kotlin

sealed interface ResultFlow<out T> {
    data class Success<T>(val data: T) : ResultFlow<T>
    data class ErrorResult(val exception: Throwable) : ResultFlow<Nothing>
    data object Loading : ResultFlow<Nothing>
}