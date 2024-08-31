package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.testchat.domain.chats.entity.ChatDownloadData
import ru.ivan.eremin.testchat.domain.chats.entity.ChatMessageItem

@Composable
fun ChatMessageItemView(
    item: ChatMessageItem,
    downloadFileState: (id: String) -> ChatDownloadData?,
    onClick: (ChatMessageItem) -> Unit,
    onQuickActionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (item) {
        is ChatMessageItem.SendMessage -> SendMessageView(
            message = item,
            onClick = onClick,
            modifier = modifier.padding(bottom = 8.dp)
        )

        is ChatMessageItem.ReceivedMessage -> ReceivedMessageView(
            message = item,
            onQuickActionClick = onQuickActionClick,
            modifier = modifier.padding(bottom = 8.dp)
        )

        is ChatMessageItem.ReceivedFileMessage -> ReceivedFileView(
            message = item,
            downloadFileState = downloadFileState,
            onClick = onClick,
            modifier = modifier.padding(bottom = 8.dp)
        )


        is ChatMessageItem.ReceivedImageMessage -> ReceivedImageView(
            message = item,
            downloadFileState = downloadFileState,
            onClick = onClick,
            modifier.padding(bottom = 8.dp)
        )

        is ChatMessageItem.SendImageMessage -> SendImageView(
            message = item,
            downloadFileState = downloadFileState,
            onClick = onClick,
            modifier.padding(bottom = 8.dp)
        )

        is ChatMessageItem.Date -> ChatDateView(date = item, modifier.padding(bottom = 8.dp))
    }
}