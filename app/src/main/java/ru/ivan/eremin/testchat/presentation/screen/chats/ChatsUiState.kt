package ru.ivan.eremin.testchat.presentation.screen.chats

import ru.ivan.eremin.testchat.domain.chats.entity.Chat
import ru.ivan.eremin.testchat.presentation.core.UiError
import ru.ivan.eremin.testchat.presentation.core.UiState

data class ChatsUiState (
    val skeleton: Boolean = false,
    val refresh: Boolean = false,
    val chats: List<Chat>? =null,
    val error: UiError? = null
) : UiState