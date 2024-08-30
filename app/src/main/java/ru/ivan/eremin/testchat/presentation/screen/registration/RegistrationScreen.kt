@file:OptIn(ExperimentalMaterial3Api::class)

package ru.ivan.eremin.testchat.presentation.screen.registration


import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import ru.ivan.eremin.testchat.presentation.components.Screen
import ru.ivan.eremin.testchat.presentation.navigate.ChatsRoute

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RegistrationScreen(
    navHostController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val resuestPermissionState = rememberPermissionState(
        permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            Manifest.permission.READ_PHONE_NUMBERS
        else Manifest.permission.READ_PHONE_STATE
    ) {
        if (it) {
            viewModel.loadData()
        }
    }

    LaunchedEffect(Unit) {
        if (resuestPermissionState.status == PermissionStatus.Granted) {
            viewModel.loadData()
        } else {
            resuestPermissionState.launchPermissionRequest()
        }
    }

    RegistrationScreenState(
        state = state,
        onAction = remember {
            {
                handleAction(it, navHostController)
            }
        }
    )
}

@Composable
private fun RegistrationScreenState(
    state: RegistrationUiState,
    onAction: (Action) -> Unit
) {
    Screen(
        topBar = {
            TopAppBar(title = { Text(text = "Реагистрация") })
        },
        uiError = state.error
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = state.phone.orEmpty(),
                onValueChange = {},
                readOnly = true,
            )
            TextField(value = "Иван", onValueChange = {})
            TextField(value = "warsontt00", onValueChange = {})
            Button(onClick = {
                onAction(Action.Registration)
            }) {
                Text(text = "Зарегистрироваться")
            }
        }
    }
}

internal sealed interface Action {
    data object Registration : Action
}

private fun handleAction(action: Action, navHostController: NavHostController) {
    when (action) {
        is Action.Registration -> {
            navHostController.navigate(
                ChatsRoute.route
            ) {
                launchSingleTop = true
                restoreState = true
                popUpTo(navHostController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewRegistrationScreen() {
    MaterialTheme {
        RegistrationScreenState(state = RegistrationUiState(), onAction = {})
    }
}