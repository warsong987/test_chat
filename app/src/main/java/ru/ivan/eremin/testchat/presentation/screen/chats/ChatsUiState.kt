package ru.ivan.eremin.testchat.presentation.screen.chats

import ru.ivan.eremin.feature.entity.UiError
import ru.ivan.eremin.feature.entity.UiState
import ru.ivan.eremin.testchat.domain.chats.entity.Chat

data class ChatsUiState (
    val skeleton: Boolean = false,
    val refresh: Boolean = false,
    val chats: List<Chat>? =null,
    val error: UiError? = null
) : UiState