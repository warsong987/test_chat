@file:OptIn(ExperimentalMaterial3Api::class)

package ru.ivan.eremin.testchat.presentation.screen.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.ivan.eremin.components.BottomNavigate
import ru.ivan.eremin.components.ErrorImage
import ru.ivan.eremin.feature.base.Screen
import ru.ivan.eremin.navigate.Chats
import ru.ivan.eremin.testchat.domain.chats.entity.Chat

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
        onAction = remember {
            {
                handleAction(it, navHostController)
            }
        }
    )
}

@ExperimentalMaterial3Api
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
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.chats.orEmpty(), key = { chat -> chat.id }) { chat ->
                ChatItem(
                    modifier = Modifier.fillMaxWidth(),
                    chat = chat,
                    onAction = onAction
                )

            }
        }
    }
}

@Composable
private fun ChatItem(
    modifier: Modifier = Modifier,
    chat: Chat, onAction: (ChatsAction) -> Unit
) {
    Card(
        modifier = modifier,
        onClick = { onAction(ChatsAction.SelectChat(chat.id)) }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.size(40.dp),
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
        is ChatsAction.SelectChat -> navHostController.navigate(ru.ivan.eremin.navigate.Chat(action.chatId)) {
            popUpTo(Chats) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
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