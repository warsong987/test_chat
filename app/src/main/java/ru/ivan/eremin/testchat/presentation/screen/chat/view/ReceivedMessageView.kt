package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.components.TextMessageContainer
import ru.ivan.eremin.testchat.domain.chats.entity.ChatMessageItem

@Composable
fun ReceivedMessageView(
    message: ChatMessageItem.ReceivedMessage,
    onQuickActionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentOnQuickActionClick by rememberUpdatedState(newValue = onQuickActionClick)
    Column(modifier) {
        ReceivedItemBox(
            avatar = {
                message.avatar?.let {
                    ChatAvatarView(it)
                }
            },
            message = {
                Card(
                    shape = RoundedCornerShape(
                        topStart = animateDpAsState(
                            if (message.roundTop) 24.dp else 2.dp,
                            label = "RoundTopStart"
                        ).value,
                        bottomStart = animateDpAsState(
                            if (message.roundBottom) 24.dp else 2.dp,
                            label = "RoundBottomStart"
                        ).value,
                        topEnd = 24.dp,
                        bottomEnd = 24.dp
                    )
                ) {
                    val time = remember(message) { message.date.toString() }
                    TextMessageContainer(
                        message = message.text,
                        time = time,
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    )
                }
            }
        )
        AnimatedVisibility(message.quickActions.isNotEmpty()) {
            ReceivedItemBox(
                avatar = {},
                message = {
                    Card() {
                        Column(modifier = Modifier.width(IntrinsicSize.Min)) {
                            val lastIndex = message.quickActions.lastIndex
                            message.quickActions.forEachIndexed { index, action ->
                                QuickAction(
                                    text = action,
                                    onClick = { currentOnQuickActionClick(it) })
                                if (index != lastIndex) HorizontalDivider()
                            }
                        }
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun QuickAction(
    text: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onClick.invoke(text) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .width(IntrinsicSize.Max)
        )
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.AutoMirrored.Default.ArrowForward,
            contentDescription = null,
        )
    }
}