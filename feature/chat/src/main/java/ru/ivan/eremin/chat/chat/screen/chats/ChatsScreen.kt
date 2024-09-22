@file:OptIn(ExperimentalMaterial3Api::class)

package ru.ivan.eremin.chat.chat.screen.chats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.ivan.eremin.components.BottomNavigate
import ru.ivan.eremin.feature.base.Screen
import ru.ivan.eremin.navigate.Chats

@ExperimentalMaterial3Api
@Composable
fun ChatsScreen(
    navHostController: NavHostController,
    viewModel: ChatsViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()

    ChatsScreenState(
        state,
        navHostController,

    )
}

@ExperimentalMaterial3Api
@Composable
private fun ChatsScreenState(
    state: ChatsUiState,
    navHostController: NavHostController
) {
    Screen(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = Chats.resourceId!!))
                }
            )
        },
        bottomBar = {
            BottomNavigate(navController = navHostController)
        },
        uiError = state.error,
        isRefreshing = state.refresh,
    ) {

    }
}
