package ru.ivan.eremin.testchat.domain.chats.entity

import androidx.compose.runtime.Immutable

@Immutable
data class ChatAuthor(
    val nickname: String,
    val participantId: String,
    val type: ChatAuthorType,
    val avatar: String
)
