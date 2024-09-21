package ru.ivan.eremin.feature.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.components.LocalRefreshHandler
import ru.ivan.eremin.components.ProvideRefreshHandler
import ru.ivan.eremin.components.PullToRefreshContainer
import ru.ivan.eremin.components.Scaffold
import ru.ivan.eremin.feature.R
import ru.ivan.eremin.feature.entity.UiError
import ru.ivan.eremin.feature.entity.getErrorMessage


@ExperimentalMaterial3Api
@Composable
fun Screen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    uiError: UiError? = null,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    contentWindowInsets: WindowInsets = WindowInsets.Companion.safeDrawing,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    floatingActionButtonPosition: FabPosition = FabPosition.Center,
    content: @Composable BoxScope.() -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    ScreenContent(
        modifier,
        snackbarHostState = snackbarHostState,
        uiError = uiError,
        onRefresh = onRefresh,
        content = {
            val refreshHandler = LocalRefreshHandler.current
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (scrollBehavior != null) {
                            Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                        } else {
                            Modifier
                        }
                    ),
                topBar = topBar,
                bottomBar = {
                    Box(contentAlignment = Alignment.BottomCenter) {
                        Spacer(modifier = Modifier.windowInsetsBottomHeight(contentWindowInsets))
                        bottomBar()
                    }
                },
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                snackbarHost = { SnackbarHost(snackbarHostState) },
                contentWindowInsets = WindowInsets(0.dp),
                containerColor = backgroundColor,
                content = { padding ->
                    val pullToRefreshState = rememberPullToRefreshState()
                    PullToRefreshContainer(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .clipToBounds(),
                        state = pullToRefreshState,
                        isRefreshing = isRefreshing,
                        onRefresh = onRefresh,
                        content = {
                            content()
                            if (isRefreshing) {
                                LaunchedEffect(true) {
                                    onRefresh?.invoke()
                                    refreshHandler.refresh()
                                }
                            }
                            LaunchedEffect(isRefreshing) {
                                if (isRefreshing) pullToRefreshState.animateToThreshold()
                                else pullToRefreshState.animateToHidden()
                            }
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    uiError: UiError? = null,
    onRefresh: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    ProvideRefreshHandler {
        Box(modifier = modifier) {
            content()
        }
        val error = uiError.getErrorMessage()
        val snackbarAction = stringResource(id = R.string.retry)
        val refreshHandler = LocalRefreshHandler.current
        LaunchedEffect(error, uiError) {
            if (error != null) {
                snackbarHostState.showSnackbar(
                    error,
                    actionLabel = snackbarAction,
                    duration = SnackbarDuration.Indefinite
                ).let {
                    if (it == SnackbarResult.ActionPerformed) {
                        refreshHandler.refresh()
                        onRefresh?.invoke()
                    }
                }
            } else {
                snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }
}
