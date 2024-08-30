package ru.ivan.eremin.testchat.domain.chats

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Message(
    val userMessage: String,
    val otherMessages: String
)
