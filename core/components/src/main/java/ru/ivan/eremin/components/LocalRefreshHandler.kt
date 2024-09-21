package ru.ivan.eremin.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch


val LocalRefreshHandler = compositionLocalOf<RefreshHandler> {
    object : RefreshHandler {
        override val refreshState: Flow<Unit>
            get() = emptyFlow()

        override fun refresh() {
            // no-op
        }
    }
}

@Stable
interface RefreshHandler {
    val refreshState: Flow<Unit>
    fun refresh()
}

class CustomRefreshHandel(
    private val scope: CoroutineScope,
    private val parent: RefreshHandler? = null
) : RefreshHandler {
    private val _refreshHandler = MutableSharedFlow<Unit>()
    override val refreshState: Flow<Unit> = _refreshHandler
    override fun refresh() {
        parent?.refresh()
        scope.launch {
            delay(DELAY)
            _refreshHandler.emit(Unit)
        }
    }

    private companion object {
        const val DELAY = 200L
    }
}

@Composable
fun ProvideRefreshHandler(updateParent: Boolean = false, content: @Composable () -> Unit) {
    val current = if (updateParent) LocalRefreshHandler.current else null
    val scope = rememberCoroutineScope()
    val new = remember {
        CustomRefreshHandel(scope, current)
    }
    CompositionLocalProvider(LocalRefreshHandler provides new, content = content)
}