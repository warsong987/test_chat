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
import ru.ivan.eremin.testchat.domain.chats.entity.SendMessageState

@Composable
fun SendImageView(
    message: ChatMessageItem.SendImageMessage,
    downloadFileState: (id: String) -> ChatDownloadData?,
    onClick: (ChatMessageItem.SendImageMessage) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentDownloadFileState by rememberUpdatedState(downloadFileState)
    val currentOnClick by rememberUpdatedState(onClick)
    SendItemBox(
        modifier = modifier,
        progress = {
            SendMessageProgressView(
                message.progress,
                showProgress = false,
                onClick = { currentOnClick(message) },
            )
        },
        message = {
            Box(contentAlignment = Alignment.CenterEnd) {
                val file = message.file
                if (file.needDownload && file.id != null) {
                    val state = currentDownloadFileState(file.id.orEmpty())
                    ChatImageCard(
                        url = state?.chatFile?.url.orEmpty(),
                        placeholderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.clickable { currentOnClick(message) }
                    )
                    state?.state?.let {
                        DownloadProgressView(
                            it,
                            showSuccess = false,
                            onClick = { currentOnClick(message) },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    ChatImageCard(
                        url = message.file.url.orEmpty(),
                        placeholderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .clickable { currentOnClick(message) }
                    )
                    if (message.progress is SendMessageState.Progress) {
                        DownloadProgressView(
                            DownloadState.PROGRESS,
                            onClick = { currentOnClick(message) },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    )
}
