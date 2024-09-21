package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.components.TextMessageContainer
import ru.ivan.eremin.testchat.domain.chats.entity.ChatMessageItem

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
                onClick = { currentOnClick(message) }
            )
        },
        message = {
            Card(
                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    bottomStart = 24.dp,
                    topEnd = animateDpAsState(
                        if (message.roundTop) 24.dp else 2.dp,
                        label = "RoundTopEnd"
                    ).value,
                    bottomEnd = animateDpAsState(
                        if (message.roundBottom) 24.dp else 2.dp,
                        label = "RoundBottomEnd"
                    ).value,
                ),
            ) {
                val time = remember(message) { message.date.toString() }
                TextMessageContainer(
                    message = message.text,
                    time = time,
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                )
            }
        }
    )
}