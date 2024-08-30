package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import ru.ivan.eremin.testchat.domain.chats.ChatMessageItem

@Composable
fun SendMessageView(
    message: ChatMessageItem.SendMessage,
    onClick: (ChatMessageItem.SendMessage) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentOnClick by rememberUpdatedState(newValue = onClick)

    SendItemBox(
        modifier = modifier,
        progress = {
            SendMessageProgressView(
                message.progress,
                onClick = {currentOnClick(message)}
            )
        },
        message = {
            Card(

            ){
                val time = remember(message) {message.date}
            }
        },
    )
}