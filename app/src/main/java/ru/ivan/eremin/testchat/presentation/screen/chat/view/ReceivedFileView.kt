package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.testchat.domain.chats.entity.ChatDownloadData
import ru.ivan.eremin.testchat.domain.chats.entity.ChatMessageItem
import ru.ivan.eremin.testchat.domain.chats.entity.DownloadState
import ru.ivan.eremin.testchat.presentation.components.Text

@Composable
fun ReceivedFileView(
    message: ChatMessageItem.ReceivedFileMessage,
    downloadFileState: (id: String) -> ChatDownloadData?,
    onClick: (ChatMessageItem.ReceivedFileMessage) -> Unit,
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
            Card() {
                Row(
                    modifier = Modifier
                        .clickable { currentOnClick(message) }
                        .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.file.name.orEmpty(),
                        maxLines = 2
                    )
                    val file = message.file
                    val state = if (file.needDownload && file.id != null) {
                        currentDownloadFileState(file.id.orEmpty())?.state ?: DownloadState.NOT_STARTED
                    } else DownloadState.SUCCESS
                    DownloadProgressView(
                        state,
                        showSuccess = true,
                        onClick = { currentOnClick(message) },
                    )
                }
            }
        }
    )
}