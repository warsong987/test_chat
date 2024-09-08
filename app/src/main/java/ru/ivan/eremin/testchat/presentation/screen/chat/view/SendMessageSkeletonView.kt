package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
internal fun SendMessageSkeletonView(
    roundTop: Boolean,
    roundBottom: Boolean,
    modifier: Modifier = Modifier
) {
    SendItemBox(
        modifier = modifier.sizeIn(maxHeight = 90.dp),
        progress = {},
        message = {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Gray,
                        RoundedCornerShape(
                            topEnd = if (roundTop) 24.dp else 2.dp,
                            bottomEnd = if (roundBottom) 24.dp else 2.dp,
                            topStart = 24.dp,
                            bottomStart = 24.dp
                        )
                    ),
            )
        }
    )
}