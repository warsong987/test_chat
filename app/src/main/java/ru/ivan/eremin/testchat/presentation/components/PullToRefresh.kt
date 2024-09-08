package ru.ivan.eremin.testchat.presentation.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PullToRefreshContainer(
    state: PullToRefreshState,
    modifier: Modifier = Modifier,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    PullToRefreshBox(
        state = state,
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = { onRefresh?.invoke() },
        content = content
    )
}


object PullToRefreshDefaults {

    val shape: Shape = CircleShape
    val containerColor: Color @Composable get() = MaterialTheme.colorScheme.background
    val contentColor: Color @Composable get() = MaterialTheme.colorScheme.primary
}
