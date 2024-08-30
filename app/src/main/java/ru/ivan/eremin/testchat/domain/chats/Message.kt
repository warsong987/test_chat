package ru.ivan.eremin.testchat.domain.chats

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Message(
    val userMessage: String,
    val otherMessages: String
)

@Immutable
@Serializable
data class ChatDetails(
    val icon: String,
    val name: String,
    val status: String,
    val messages: List<Message>
)