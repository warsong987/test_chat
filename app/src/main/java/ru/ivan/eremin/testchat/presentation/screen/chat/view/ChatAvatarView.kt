package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ChatAvatarView(
    url: String?,
    modifier: Modifier = Modifier,
    skeleton: Boolean = false
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .requiredSize(36.dp)
            .then(if(skeleton)Modifier.background(Color.LightGray) else Modifier),
        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceContainer),
        error = ColorPainter(MaterialTheme.colorScheme.errorContainer),
    )


}