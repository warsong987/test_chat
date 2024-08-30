package ru.ivan.eremin.testchat.presentation.screen.chat

import ru.ivan.eremin.testchat.domain.chats.Message
import ru.ivan.eremin.testchat.presentation.core.UiError
import ru.ivan.eremin.testchat.presentation.core.UiState

data class ChatUiState(
    val skeleton: Boolean = false,
    val refresh: Boolean = false,
    val message: List<Message>? = null,
    val error: UiError? = null
): UiState
