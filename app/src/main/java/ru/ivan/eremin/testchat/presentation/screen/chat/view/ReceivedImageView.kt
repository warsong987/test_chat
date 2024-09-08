package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.ivan.eremin.testchat.domain.chats.entity.ChatDownloadData
import ru.ivan.eremin.testchat.domain.chats.entity.ChatMessageItem
import ru.ivan.eremin.testchat.domain.chats.entity.DownloadState

@Composable
internal fun ReceivedImageView(
    message: ChatMessageItem.ReceivedImageMessage,
    downloadFileState: (id: String) -> ChatDownloadData?,
    onClick: (ChatMessageItem.ReceivedImageMessage) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentDownloadFileState by rememberUpdatedState(downloadFileState)
    val currentOnClick by rememberUpdatedState(onClick)
    ReceivedItemBox(
        modifier = modifier,
        avatar = {
            message.avatar?.let {
                ChatAvatarView(url = it)
            }
        },
        message = {
            Box(contentAlignment = Alignment.CenterStart) {
                val file = message.file
                if (file.needDownload && file.id != null) {
                    val downloadState = currentDownloadFileState(file.id.orEmpty())
                    val state = if (file.needDownload && file.id != null) {
                        downloadState?.state ?: DownloadState.NOT_STARTED
                    } else DownloadState.SUCCESS
                    ChatImageCard(
                        url = downloadState?.chatFile?.url.orEmpty(),
                        placeholderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.clickable { currentOnClick(message) }
                    )
                    DownloadProgressView(
                        state,
                        showSuccess = false,
                        onClick = { currentOnClick(message) },
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    ChatImageCard(
                        url = message.file.url.orEmpty(),
                        placeholderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.clickable { currentOnClick(message) }
                    )
                }
            }
        }
    )
}