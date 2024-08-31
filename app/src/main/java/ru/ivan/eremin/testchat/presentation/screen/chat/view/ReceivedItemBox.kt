package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReceivedItemBox(
    avatar: @Composable BoxScope.() -> Unit,
    message: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Box(modifier = Modifier.width(44.dp), content = avatar)
        Box(
            Modifier
                .fillMaxWidth()
                .padding(end = 24.dp),
            content = message
        )
    }
}