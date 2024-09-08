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
internal fun ReceivedMessageSkeletonView(
    showAvatar: Boolean,
    roundTop: Boolean,
    roundBottom: Boolean,
    modifier: Modifier = Modifier
) {
    ReceivedItemBox(
        modifier = modifier.sizeIn(maxHeight = 90.dp),
        avatar = {
            if (showAvatar) {
                ChatAvatarView(null, skeleton = true)
            }
        },
        message = {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Gray, RoundedCornerShape(
                            topStart = if (roundTop) 24.dp else 2.dp,
                            bottomStart = if (roundBottom) 24.dp else 2.dp,
                            topEnd = 24.dp,
                            bottomEnd = 24.dp
                        )
                    )
            )
        }
    )
}