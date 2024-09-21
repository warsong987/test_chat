package ru.ivan.eremin.testchat.presentation.screen.chat

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import ru.ivan.eremin.feature.base.BaseViewModel
import ru.ivan.eremin.navigate.Chat
import ru.ivan.eremin.testchat.domain.chats.entity.ChatDetails
import ru.ivan.eremin.testchat.domain.chats.entity.ChatMessageItem
import ru.ivan.eremin.testchat.domain.chats.entity.ChatSelectedFile
import ru.ivan.eremin.testchat.domain.chats.entity.Message

class ChatViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ChatUiState>() {

    private val chatId by lazy { savedStateHandle.toRoute<Chat>().id }

    override fun createInitialState(): ChatUiState {
        return ChatUiState()
    }

    init {
        updateState {
            copy(
                message = ChatDetails(
                    icon = "",
                    name = "Chat",
                    messages = List(5) {
                        Message(
                            userMessage = "$it UserMessage",
                            otherMessages = "$it OtherMessages"
                        )
                    },
                    status = "Онлайн"
                )
            )
        }
    }

    fun deleteFileFromSelected(chatFile: ChatSelectedFile){

    }

    fun changeMessage(message: String) {

    }

    fun sendMessage() {

    }

    fun clickItem(item: ChatMessageItem) {

    }

    fun chooseFile() {

    }

    fun sendQuickAction(text: String) {

    }

    fun loadNextHistoryPage() {

    }
}