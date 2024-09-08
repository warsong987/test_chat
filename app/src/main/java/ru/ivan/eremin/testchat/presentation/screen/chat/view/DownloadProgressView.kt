package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.testchat.domain.chats.entity.DownloadState

@Composable
internal fun DownloadProgressView(
    state: DownloadState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showSuccess: Boolean = true
) {
    if (showSuccess || state != DownloadState.SUCCESS) {
        Card(
            modifier = modifier.size(36.dp).clickable { onClick()},
            shape = CircleShape,
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if(state == DownloadState.PROGRESS) {
                    CircularProgressIndicator(
                        color = LocalContentColor.current,
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxSize(),
                        strokeWidth = 1.dp
                    )
                }
                Crossfade(
                    targetState = state.getIcon(),
                    label = "updateIcon",
                    modifier = Modifier.size(16.dp)
                ) {
                    Icon(
                        it,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
private fun DownloadState.getIcon(): ImageVector {
    return when (this) {
        DownloadState.PROGRESS ->  Icons.Default.Close
        DownloadState.NOT_STARTED, DownloadState.ERROR -> Icons.Default.Info
        DownloadState.SUCCESS -> Icons.Default.Check
    }
}
