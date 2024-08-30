package ru.ivan.eremin.testchat.presentation.screen.chat

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import ru.ivan.eremin.testchat.domain.chats.ChatDetails
import ru.ivan.eremin.testchat.domain.chats.ChatDownloadData
import ru.ivan.eremin.testchat.domain.chats.ChatMessageItem
import ru.ivan.eremin.testchat.domain.chats.ChatSelectedFile
import ru.ivan.eremin.testchat.presentation.core.UiError
import ru.ivan.eremin.testchat.presentation.core.UiState
import ru.ivan.eremin.testchat.presentation.screen.chat.entity.ChatStatus

data class ChatUiState(
    val messagesState: MessageState = MessageState(),
    val historyState: HistoryState = HistoryState(),
    val sessionState: SessionState = SessionState(),
    val chatState: ChatState = ChatState(),
    val fileDownloadState:FileDownloadState = FileDownloadState(),
    val chatStatus: ChatStatus = ChatStatus.Empty,
    val skeleton: Boolean = false,
    val refresh: Boolean = false,
    val message: ChatDetails? = null,
    val error: UiError? = null
) : UiState {
    data class MessageState(
        val selectedFiles: ImmutableList<ChatSelectedFile> = persistentListOf(),
        val message: String = ""
    ) : UiState {
        val showSend: Boolean
            get() = message.isNotEmpty() || selectedFiles.isNotEmpty()
    }

    data class HistoryState(
        val page: Int = 0,
        val pageCount: Int = 0,
        val messages: ImmutableList<ChatMessageItem> = persistentListOf(),
        val error: UiError? = null,
        val loading: Boolean = true,
        val firstDataLoading: Boolean = true
    ) : UiState

    data class SessionState(
        val loading: Boolean = true,
        val messages: List<ChatMessageItem> = mutableListOf()
    ) : UiState

    data class ChatState(
        val loading: Boolean = true,
        val error: UiError? = null,
        val blocking: Boolean = false,
        val action: ImmutableList<String> = persistentListOf(),
    ) : UiState

    data class FileDownloadState(
        val files: ImmutableMap<String, ChatDownloadData> = persistentMapOf()
    ) : UiState
}
