package ru.ivan.eremin.testchat.domain.chats.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class Chat(
    val id: Int,
    val name: String,
    val persons: List<Person>,
    val lastMessage: String,
    val icon: String
) {
    @Serializable
    @Immutable
    data class Person(
        val name: String,
    )
}
