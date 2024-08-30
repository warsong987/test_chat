@file:OptIn(ExperimentalMaterial3Api::class)

package ru.ivan.eremin.testchat.presentation.screen.authorization


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import ru.ivan.eremin.testchat.R
import ru.ivan.eremin.testchat.presentation.components.CodeTextField
import ru.ivan.eremin.testchat.presentation.components.Screen
import ru.ivan.eremin.testchat.presentation.navigate.ChatsRoute
import ru.ivan.eremin.testchat.presentation.navigate.RegistrationRoute
import ru.ivan.eremin.testchat.presentation.utils.textfield.MaskVisualTransformation

@Composable
fun AuthorizationScreen(
    navHostController: NavHostController,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        snapshotFlow { state.events }
            .filterNotNull()
            .collectLatest { events ->
                events.firstOrNull()?.let { event ->
                    eventHandler(event, navHostController)
                    viewModel.removeEvent(event)
                }
            }
    }

    AuthorizationScreenState(
        state = state,
        onAction = remember {
            {
                actionHandler(it, viewModel)
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AuthorizationScreenState(
    state: AuthorizationUiState,
    onAction: (Action) -> Unit
) {
    Screen(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.authorization)
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        uiError = state.error
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = state.phone.orEmpty(),
                onValueChange = {
                    val phone = it.filter { it.isDigit() }
                    if (phone.length <= 10) {
                        onAction(Action.ChangePhone(phone))
                    }
                },
                isError = state.errorPhone != null,
                supportingText = {
                    if (state.errorPhone != null) {
                        Text(
                            text = state.errorPhone.getErrorText(),
                            color = MaterialTheme.colorScheme.errorContainer
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                visualTransformation = MaskVisualTransformation("+7 (###) ###-##-##")
            )

            if (state.isSuccessSendPhone) {
                CodeTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.code.orEmpty(),
                    onValueChange = {
                        onAction(Action.ChangeCode(it))
                    },
                    isError = state.codeError != null,
                    errorText = state.codeError?.getErrorText(),
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(
                        if (state.isSuccessSendPhone) {
                            Action.Authorization(state.phone.orEmpty(), state.code.orEmpty())
                        } else {
                            Action.SendPhone(state.phone.orEmpty())
                        }
                    )
                },
                enabled = if (state.isSuccessSendPhone) {
                    state.isActionCheckAuthorization
                } else {
                    state.isActionSendPhone
                }
            ) {
                Text(text = getActionText(isSuccessSendPhone = state.isSuccessSendPhone))
            }
        }
    }
}

@Composable
@ReadOnlyComposable
private fun getActionText(isSuccessSendPhone: Boolean): String {
    return if (isSuccessSendPhone) {
        stringResource(id = R.string.authorization)
    } else {
        stringResource(id = R.string.send_phone_get_code)
    }
}

private fun eventHandler(event: AuthorizationEvent, navHostController: NavHostController) {
    when (event) {
        is AuthorizationEvent.Authorization -> {
            if (event.userIsExist) {
                navHostController.navigate(ChatsRoute.route) {
                    params( navHostController)
                }
            } else {
                navHostController.navigate("${RegistrationRoute.route}?phone=${event.phone}") {
                    params(navHostController)
                }
            }

        }
    }
}

private fun NavOptionsBuilder.params(navHostController: NavHostController) {
    popUpTo(navHostController.graph.findStartDestination().id) {
        inclusive = true
    }
}

private fun actionHandler(action: Action, viewModel: AuthorizationViewModel) {
    when (action) {
        is Action.Authorization -> viewModel.checkCode(action.phone, action.code)
        is Action.SendPhone -> viewModel.sendPhone(action.phone)
        is Action.ChangeCurrentZip -> viewModel.setZip(action.zip)
        is Action.ChangePhone -> viewModel.changePhone(action.phone)
        is Action.ChangeCode -> viewModel.changeCode(action.code)
    }
}

private sealed interface Action {
    data class Authorization(val phone: String, val code: String) : Action
    data class SendPhone(val phone: String) : Action
    data class ChangePhone(val phone: String) : Action
    data class ChangeCode(val code: String) : Action
    data class ChangeCurrentZip(val zip: String) : Action
}

@Composable
@Preview
private fun AuthorizationScreenPreview() {
    MaterialTheme {
        AuthorizationScreenState(
            state = AuthorizationUiState(
                isSuccessSendPhone = true
            ),
            onAction = {}
        )
    }
}