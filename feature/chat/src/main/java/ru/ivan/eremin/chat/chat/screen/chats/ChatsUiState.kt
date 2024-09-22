package ru.ivan.eremin.chat.chat.screen.chats

import ru.ivan.eremin.feature.entity.UiError
import ru.ivan.eremin.feature.entity.UiState

data class ChatsUiState (
    val skeleton: Boolean = false,
    val refresh: Boolean = false,
    val error: UiError? = null
) : UiState