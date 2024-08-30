@file:OptIn(ExperimentalMaterial3Api::class)

package ru.ivan.eremin.testchat.presentation.screen.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import ru.ivan.eremin.testchat.domain.chats.Chat
import ru.ivan.eremin.testchat.presentation.components.BottomNavigate
import ru.ivan.eremin.testchat.presentation.components.ErrorImage
import ru.ivan.eremin.testchat.presentation.components.Screen
import ru.ivan.eremin.testchat.presentation.navigate.ChatRoute
import ru.ivan.eremin.testchat.presentation.navigate.ChatsRoute

@Composable
fun ChatsScreen(
    navHostController: NavHostController,
    viewModel: ChatsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    ChatsScreenState(
        state,
        navHostController,
        onAction = remember {
            {
                handleAction(it, navHostController)
            }
        }
    )
}

@Composable
private fun ChatsScreenState(
    state: ChatsUiState,
    navHostController: NavHostController,
    onAction: (ChatsAction) -> Unit
) {
    Screen(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = ChatsRoute.resourceId!!))
                }
            )
        },
        bottomBar = {
            BottomNavigate(navController = navHostController)
        },
        uiError = state.error,
        isRefreshing = state.refresh,
    ) {
        LazyColumn {
            items(state.chats.orEmpty(), key = { chat -> chat.id }) { chat ->
                ChatItem(
                    modifier
                    chat = chat,
                    onAction = onAction
                )

            }
        }
    }
}

@Composable
private fun ChatItem(chat: Chat, onAction: (ChatsAction) -> Unit) {
    Card(
        onClick = { onAction(ChatsAction.SelectChat(chat.id)) }
    ) {
        Row {
            SubcomposeAsyncImage(
                model = chat.icon,
                contentDescription = null,
                loading = {
                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                    )
                },
                error = {
                    ErrorImage()
                }
            )
            Column(

            ) {
                Text(text = chat.name)
                Text(text = chat.lastMessage)

            }
        }
    }
}

sealed interface ChatsAction {
    data class SelectChat(val chatId: Int) : ChatsAction
}

private fun handleAction(action: ChatsAction, navHostController: NavHostController) {
    when (action) {
        is ChatsAction.SelectChat -> navHostController.navigate("${ChatRoute.route}/${action.chatId}")
    }
}

@Composable
@Preview
private fun ChatsScreenPreview() {
    MaterialTheme {
        val navHostController = rememberNavController()
        ChatsScreenState(
            state = ChatsUiState(
                chats = listOf(
                    Chat(
                        id = 1,
                        name = "Test chat",
                        persons = listOf(
                            Chat.Person("Иван")
                        ),
                        lastMessage = "Hello, world!",
                        icon = ""
                    )
                )
            ),
            navHostController = navHostController,
            onAction = {}
        )
    }
}