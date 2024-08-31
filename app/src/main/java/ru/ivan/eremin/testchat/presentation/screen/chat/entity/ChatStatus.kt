package ru.ivan.eremin.testchat.presentation.screen.chat.entity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import ru.ivan.eremin.testchat.R
import ru.ivan.eremin.testchat.domain.chats.entity.ChatAuthor

@Immutable
sealed interface ChatStatus {
    data object Empty : ChatStatus
    data object Connecting : ChatStatus
    data class Typing(val from: ChatAuthor) : ChatStatus
    data class Online(val from: ChatAuthor) : ChatStatus
}

@Composable
@ReadOnlyComposable
internal fun ChatStatus.getText(): String? {
    return when (this) {
        is ChatStatus.Empty -> null
        is ChatStatus.Connecting -> stringResource(id = R.string.chat_status_connecting)
        is ChatStatus.Typing -> stringResource(id = R.string.chat_status_typing, from.nickname)
        is ChatStatus.Online -> stringResource(id = R.string.chat_status_online, from.nickname)
    }
}
