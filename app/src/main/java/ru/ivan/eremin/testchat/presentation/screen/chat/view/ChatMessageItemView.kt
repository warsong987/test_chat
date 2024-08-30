package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.ivan.eremin.testchat.domain.chats.ChatDownloadData
import ru.ivan.eremin.testchat.domain.chats.ChatMessageItem

@Composable
fun ChatMessageItemView(
    item: ChatMessageItem,
    downloadFileState:(id: String) -> ChatDownloadData?,
    onClick: (ChatMessageItem) -> Unit,
    onQuickActionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when(item){
        is
    }
}