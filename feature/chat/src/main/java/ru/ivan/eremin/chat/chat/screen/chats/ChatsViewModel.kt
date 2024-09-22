package ru.ivan.eremin.chat.chat.screen.chats

import ru.ivan.eremin.feature.base.BaseViewModel


class ChatsViewModel() : BaseViewModel<ChatsUiState>() {

    override fun createInitialState(): ChatsUiState {
        return ChatsUiState()
    }

}