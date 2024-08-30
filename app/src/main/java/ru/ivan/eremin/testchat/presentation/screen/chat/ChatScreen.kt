package ru.ivan.eremin.testchat.presentation.screen.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun ChatScreen(
    navHostController: NavHostController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    ChatScreens(state = state)

}

@Composable
private fun ChatScreens(
    state: ChatUiState
){

}