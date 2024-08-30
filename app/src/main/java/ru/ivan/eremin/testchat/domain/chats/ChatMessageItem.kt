package ru.ivan.eremin.testchat.domain.chats

import androidx.compose.runtime.Immutable
import java.util.Date as DateTime

@Immutable
sealed interface ChatMessageItem {
    val id: Any
    val date: DateTime
    val type: ChatListMessageType

    data class Date(
        override val date: DateTime,
        override val id: Any
    ) : ChatMessageItem {
        override val type: ChatListMessageType
            get() = ChatListMessageType.DATE
    }

    data class ReceivedMessage(
        override val id: Long,
        val text: String,
        val avatar: String?,
        override val date: DateTime,
        val quickActions: List<String>,
        val roundTop: Boolean,
        val roundBottom: Boolean
    ) : ChatMessageItem {
        override val type: ChatListMessageType
            get() = ChatListMessageType.RECEIVED_MESSAGE
    }

    data class SendMessage(
        override val id: Long,
        val text: String,
        val progress: SendMessageState,
        override val date: DateTime,
        val roundTop: Boolean,
        val roundBottom: Boolean
    ) : ChatMessageItem {
        override val type: ChatListMessageType
            get() = ChatListMessageType.SENDED_MESSAGE
    }

    data class ReceivedImageMessage(
        override val id: Long,
        val file: ChatFile,
        val avatar: String?,
        override val date: DateTime
    ) : ChatMessageItem {
        override val type: ChatListMessageType
            get() = ChatListMessageType.RECEIVED_IMAGER
    }

    data class SendImageMessage(
        override val id: Long,
        val file: ChatFile,
        val progress: SendMessageState,
        override val date: DateTime
    ) : ChatMessageItem {
        override val type: ChatListMessageType
            get() = ChatListMessageType.SENDED_IMAGE
    }

    data class ReceivedFileMessage(
        override val id: Long,
        val file: ChatFile,
        val avatar: String?,
        override val date: DateTime,
    ) : ChatMessageItem {
        override val type: ChatListMessageType
            get() = ChatListMessageType.RECEIVED_FILE
    }

    data class Nickname(
        val nickname: String,
        val operatorType: ChatAuthorType,
        override val date: DateTime,
        override val id: String
    ) : ChatMessageItem {
        override val type: ChatListMessageType
            get() = ChatListMessageType.NICKNAME
    }

    data class Notice(
        val notice: ChatNotice,
        override val date: DateTime,
        override val id: Long,
    ) : ChatMessageItem {
        override val type: ChatListMessageType
            get() = ChatListMessageType.NOTICE
    }

    data class Rating(
        val rating: Int,
        val message: String,
        val progress: SendMessageState,
        override val date: DateTime,
        override val id: Long,
        val roundTop: Boolean,
        val roundBottom: Boolean
    ) : ChatMessageItem {
        override val type: ChatListMessageType
            get() = ChatListMessageType.RATING
    }
}
