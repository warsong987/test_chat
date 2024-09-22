@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package ru.ivan.eremin.chat.chat.screen.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.ivan.eremin.feature.base.Screen

@Composable
fun ChatScreen(
    navHostController: NavHostController,
    viewModel: ChatViewModel = koinViewModel()
) {

    ChatScreenState()
}

@Composable
private fun ChatScreenState(
) {
    Screen(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }

                }
            )
        },
        bottomBar = {

        },
        floatingActionButton = {
        }
    ) {
    }

}