package ru.ivan.eremin.chat.chat.screen.chat

import ru.ivan.eremin.feature.base.BaseViewModel

class ChatViewModel() : BaseViewModel<ChatUiState>() {


    override fun createInitialState(): ChatUiState {
        return ChatUiState()
    }
}