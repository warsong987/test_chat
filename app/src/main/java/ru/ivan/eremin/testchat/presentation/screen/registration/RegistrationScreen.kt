@file:OptIn(ExperimentalMaterial3Api::class)

package ru.ivan.eremin.testchat.presentation.screen.registration


import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import ru.ivan.eremin.testchat.R
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
                handleAction(it, navHostController, viewModel)
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
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.registration)) })
        },
        uiError = state.error
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.phone.orEmpty(),
                onValueChange = {},
                enabled = false,
                readOnly = true,
                label = {
                    Text(
                        text = stringResource(R.string.phone)
                    )
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name.orEmpty(),
                onValueChange = { onAction(Action.ChangeName(it)) },
                label = {
                    Text(
                        text = stringResource(R.string.name)
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.username.orEmpty(),
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(R.string.username)
                    )
                }
            )
            Button(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                onClick = {
                    onAction(Action.Registration)
                }
            ) {
                Text(text = stringResource(R.string.register))
            }
        }
    }
}

internal sealed interface Action {
    data object Registration : Action
    data class ChangeName(val name: String) : Action
    data class UserName(val userName: String) : Action
}

private fun handleAction(
    action: Action,
    navHostController: NavHostController,
    viewModel: RegistrationViewModel
) {
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

        is Action.ChangeName -> {
            viewModel.changeName(action.name)
        }

        is Action.UserName -> {
            viewModel.changeUserName(action.userName)
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