package ru.ivan.eremin.testchat.presentation.screen.chat

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.ivan.eremin.testchat.domain.chats.entity.ChatDetails
import ru.ivan.eremin.testchat.domain.chats.entity.ChatMessageItem
import ru.ivan.eremin.testchat.domain.chats.entity.ChatSelectedFile
import ru.ivan.eremin.testchat.domain.chats.entity.Message
import ru.ivan.eremin.testchat.presentation.core.BaseViewModel
import ru.ivan.eremin.testchat.presentation.navigate.ChatRoute
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ChatUiState>() {

    private val id by lazy { savedStateHandle.get<Int>(ChatRoute.ID) }

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