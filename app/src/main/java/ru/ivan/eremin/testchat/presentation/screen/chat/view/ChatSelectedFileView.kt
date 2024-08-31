package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.ivan.eremin.testchat.domain.chats.entity.ChatSelectedFile

@Composable
fun ChatSelectedFileView(
    file: ChatSelectedFile,
    onDelete: (file: ChatSelectedFile) -> Unit,
    modifier: Modifier = Modifier
){
    val currentOnDelete by rememberUpdatedState(newValue = onDelete)

    Box(modifier = modifier.size(80.dp)) {
        SubcomposeAsyncImage(
            model = file.uri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = {
                Spacer(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray))
            },
            modifier = Modifier
                .padding(end = 16.dp, top = 16.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    currentOnDelete(file)
                }
                .padding(8.dp)
                .size(20.dp)
                .align(Alignment.TopEnd)
        )
    }
}