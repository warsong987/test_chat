package ru.ivan.eremin.testchat.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer as MaterialPullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults as MaterialPullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState as materialRememberPullToRefreshState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PullToRefreshContainer(
    state: PullToRefreshState,
    modifier: Modifier = Modifier,
    indicator: @Composable (PullToRefreshState) -> Unit = { pullRefreshState ->
        PullToRefreshDefaults.Indicator(state = pullRefreshState)
    },
    shape: Shape = PullToRefreshDefaults.shape,
    containerColor: Color = PullToRefreshDefaults.containerColor,
    contentColor: Color = PullToRefreshDefaults.contentColor,
) {
    MaterialPullToRefreshContainer(
        state = state,
        modifier = modifier,
        indicator = indicator,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor
    )
}

@Composable
@ExperimentalMaterial3Api
fun rememberPullToRefreshState(
    positionalThreshold: Dp = PullToRefreshDefaults.PositionalThreshold,
    enabled: () -> Boolean = { true },
): PullToRefreshState {
    return materialRememberPullToRefreshState(positionalThreshold, enabled)
}

@OptIn(ExperimentalMaterial3Api::class)
object PullToRefreshDefaults {

    val shape: Shape = CircleShape
    val containerColor: Color @Composable get() = MaterialTheme.colorScheme.background
    val contentColor: Color @Composable get() =  MaterialTheme.colorScheme.primary
    val PositionalThreshold = 80.dp

    @Composable
    fun Indicator(
        state: PullToRefreshState,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current,
    ) {
        MaterialPullToRefreshDefaults.Indicator(
            state = state,
            modifier = modifier,
            color = color
        )
    }
}
