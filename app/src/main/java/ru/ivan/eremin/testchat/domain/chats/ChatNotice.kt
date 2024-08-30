package ru.ivan.eremin.testchat.domain.chats

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ChatNotice {
    data class Transfer(val time: Long) : ChatNotice
    data class InitialWaiting(val time: Long) : ChatNotice
    data object SuccessEstimation : ChatNotice
}
