package ru.ivan.eremin.testchat.domain.chats.entity

enum class ChatAuthorType {
    USER,
    EXTERNAL;

    companion object {
        fun getByType(type: String) = entries.firstOrNull { it.name.equals(type, true) } ?: EXTERNAL
    }
}
