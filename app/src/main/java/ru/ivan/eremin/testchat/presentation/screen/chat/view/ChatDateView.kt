package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.testchat.domain.chats.entity.ChatMessageItem

@Composable
fun ChatDateView(
    date: ChatMessageItem.Date?,
    modifier: Modifier = Modifier,
    skeleton: Boolean = false,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, top = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
        ) {
            val text = remember(date) { date?.date.toString() }
            Text(
                text = text,
                modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp)
            )
        }
    }
}
