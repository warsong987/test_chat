@file:OptIn(ExperimentalMaterial3Api::class)

package ru.ivan.eremin.testchat.presentation.screen.authorization


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import ru.ivan.eremin.testchat.presentation.components.Screen
import ru.ivan.eremin.testchat.presentation.navigate.Chats

@Composable
fun AuthorizationScreen(
    navHostController: NavHostController
) {

    AuthorizationScreenState(
        onAction = remember {
            {
                actionHandler(it, navHostController)
            }
        }
    )
}

@Composable
private fun AuthorizationScreenState(
    onAction: (Action) -> Unit
) {
    Screen(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(text = "Эакран авторизации")
            Button(onClick = { onAction(Action.Authorization) }) {
                Text(text = "Авторизоваться")
            }
        }

    }
}

private fun actionHandler(action: Action, navHostController: NavHostController) {
    when (action) {
        is Action.Authorization -> navHostController.navigate(
            Chats.route
        ) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

private sealed interface Action {
    data object Authorization : Action
}