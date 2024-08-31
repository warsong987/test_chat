package ru.ivan.eremin.testchat.presentation.screen.chat.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.testchat.R
import ru.ivan.eremin.testchat.domain.chats.entity.SendMessageState

@Composable
fun SendMessageProgressView(
    state: SendMessageState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showProgress: Boolean = true,
) {
    AnimatedVisibility(
        visible = state.show(showProgress),
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        Box(
            modifier = modifier.size(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            Crossfade(
                targetState = state,
                label = "updateIcon",
                modifier = Modifier.fillMaxSize()
            ) {
                when (it) {
                    is SendMessageState.Progress -> {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            strokeWidth = 4.dp,
                            trackColor = ProgressIndicatorDefaults.circularTrackColor,
                            strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                        )
                    }

                    is SendMessageState.Error -> {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_error_24),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.error),
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    onClick()
                                }
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}

private fun SendMessageState.show(showProgress: Boolean): Boolean {
    return when (this) {
        is SendMessageState.Progress -> showProgress
        is SendMessageState.Error -> true
        else -> false
    }
}