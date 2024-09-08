package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.testchat.R

@ExperimentalMaterial3Api
@Composable
fun ChatInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onChooseFile: () -> Unit,
    showSend: Boolean,
    skeleton: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.then(if (skeleton) Modifier.background(Color.Gray) else Modifier),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                IconButton(
                    modifier = Modifier
                        .size(36.dp)
                        .then(if (skeleton) Modifier.background(Color.Gray) else Modifier),
                    onClick = onChooseFile,
                    enabled = !skeleton
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_paperclip),
                        contentDescription = null
                    )
                }
            }

            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .requiredSizeIn(minHeight = 48.dp),
                maxLines = 5,
                placeholder = {
                    Text(text = stringResource(id = R.string.chat_input_placeholder))
                },
                enabled = !skeleton,
            )

            AnimatedVisibility(
                visible = showSend,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                    IconButton(
                        modifier = Modifier
                            .size(36.dp)
                            .padding(8.dp),
                        onClick = { onSend() },
                        enabled = !skeleton
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
            }
        }
    }
}