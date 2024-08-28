@file:OptIn(ExperimentalMaterial3Api::class)

package ru.ivan.eremin.testchat.presentation.screen.authorization


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import ru.ivan.eremin.testchat.R
import ru.ivan.eremin.testchat.presentation.components.CodeTextField
import ru.ivan.eremin.testchat.presentation.components.Screen
import ru.ivan.eremin.testchat.presentation.components.phone.component.CountryCodePicker
import ru.ivan.eremin.testchat.presentation.navigate.Chats
import ru.ivan.eremin.testchat.presentation.navigate.Registration
import ru.ivan.eremin.testchat.presentation.navigate.newRootScreen

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
            TopAppBar(title = { Text(text = stringResource(id = R.string.authorization)) })
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
            CountryCodePicker(
                modifier = Modifier.fillMaxWidth(),
                text = state.phone.orEmpty(),
                onValueChange = { phone, count ->
                    onAction(Action.ChangePhone(phone, count))
                },
                isError = state.errorPhone != null,
                error = state.errorPhone?.getErrorText(),
                onCountChange = {
                    onAction(Action.OnChangeCount(it))
                }
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
                            Action.SendPhone(state.phone.orEmpty())
                        } else {
                            Action.Authorization(state.phone.orEmpty(), state.code.orEmpty())
                        }
                    )
                },
                enabled = if (state.isSuccessSendPhone) {
                    state.isActionCheckAuthorization
                } else {
                    state.isActionSendPhone
                }
            ) {
                Text(text = getActionText(isSuccessSendPhone = state.isActionSendPhone))
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
        is AuthorizationEvent.RegistrationIsSuccess -> {
            navHostController.newRootScreen(
                if (event.userIsExist) {
                    Chats
                } else {
                    Registration(phone = event.phone)
                }
            )
        }
    }
}

private fun actionHandler(action: Action, viewModel: AuthorizationViewModel) {
    when (action) {
        is Action.Authorization -> viewModel.checkCode(action.phone, action.code)
        is Action.SendPhone -> viewModel.sendPhone(action.phone)
        is Action.ChangeCurrentZip -> viewModel.setZip(action.zip)
        is Action.ChangePhone -> viewModel.changePhone(action.phone)
        is Action.ChangeCode -> viewModel.changeCode(action.code)
        is Action.OnChangeCount -> viewModel.changeCount(action.count)
    }
}

private sealed interface Action {
    data class Authorization(val phone: String, val code: String) : Action
    data class SendPhone(val phone: String) : Action
    data class ChangePhone(val phone: String, val count: Int) : Action
    data class OnChangeCount(val count: Int) : Action
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