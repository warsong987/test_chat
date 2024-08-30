package ru.ivan.eremin.testchat.domain.chats

import androidx.compose.runtime.Immutable

@Immutable
sealed interface SendMessageState {
    data class Progress(val id: Long) : SendMessageState
    data class Error(val id: Long, val error: Exception) : SendMessageState
    data object Success : SendMessageState
}
