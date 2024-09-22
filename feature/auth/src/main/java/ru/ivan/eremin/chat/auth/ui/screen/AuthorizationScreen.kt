package ru.ivan.eremin.chat.auth.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.compose.koinViewModel
import ru.ivan.eremin.chat.auth.R
import ru.ivan.eremin.components.Text
import ru.ivan.eremin.components.phone.CountryPickerOutlinedTextField
import ru.ivan.eremin.components.phone.MaskVisualTransformation
import ru.ivan.eremin.components.phone.data.CountryDetails
import ru.ivan.eremin.components.phone.utils.CountryPickerUtils
import ru.ivan.eremin.components.phone.utils.FunctionHelper.getLengthPhone
import ru.ivan.eremin.components.phone.utils.FunctionHelper.getMask
import ru.ivan.eremin.feature.base.Screen
import ru.ivan.eremin.navigate.Chats
import ru.ivan.eremin.navigate.Registration

@Composable
fun AuthorizationScreen(
    navHostController: NavHostController,
    viewModel: AuthorizationViewModel = koinViewModel()
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
        val selectedCountryState: MutableState<CountryDetails?> = remember {
            mutableStateOf(null)
        }

        val visualPhone by remember(state.phone) {
            mutableStateOf(
                TextFieldValue(
                    text = state.phone.orEmpty().filter { it.isDigit() },
                    selection = TextRange(state.phone.orEmpty().length)
                )
            )
        }

        val context = LocalContext.current
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CountryPickerOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                mobileNumber = visualPhone,
                country = selectedCountryState.value,
                isError = state.errorPhone != null,
                onMobileNumberChange = {
                    val countryDetails = CountryPickerUtils.searchCountryByNumber(
                        context,
                        it.text
                    )
                    if (countryDetails != null) {
                        selectedCountryState.value = countryDetails
                    }
                    val lengthMask = countryDetails?.getLengthPhone()
                    if (it.text.length <= (lengthMask ?: 0) || lengthMask == null) {
                        onAction(
                            Action.ChangePhone(
                                it.text,
                                countryDetails?.countryPhoneNumberCode.orEmpty(),
                                lengthMask
                            )
                        )
                    }
                },
                supportingText = if (state.errorPhone != null) {
                    {
                        Text(
                            text = state.errorPhone.getErrorText(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                } else {
                    null
                },
                onCountrySelected = {
                    if (selectedCountryState.value != it || selectedCountryState.value == null) {
                        selectedCountryState.value = it
                    }
                    onAction(
                        Action.ChangePhone(
                            it.countryPhoneNumberCode,
                            it.countryPhoneNumberCode,
                            it.getLengthPhone()
                        )
                    )
                },
                visualTransformation = selectedCountryState.value?.getMask().orEmpty().let {
                    MaskVisualTransformation(
                        mask = it
                    )
                },
                defaultCountryCode = selectedCountryState.value?.countryCode
            )

            if (state.isSuccessSendPhone) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    value = state.code.orEmpty(),
                    onValueChange = { onAction(Action.ChangeCode(it)) },
                    isError = state.codeError != null,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    supportingText = if (state.codeError != null) {
                        {
                            Text(
                                text = state.codeError.getErrorText(),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    } else {
                        null
                    },
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = {
                    onAction(
                        if (state.isSuccessSendPhone) {
                            Action.Authorization(state.phone.orEmpty(), state.code.orEmpty())
                        } else {
                            Action.SendPhone
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
                navHostController.navigate(Chats) {
                    params(navHostController)
                }
            } else {
                navHostController.navigate(Registration(event.phone)) {
                    params(navHostController)
                }
            }
        }

        is AuthorizationEvent.OpenRegistration -> {
            navHostController.navigate(Registration(event.phone)) {
                params(navHostController)
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
        is Action.SendPhone -> viewModel.sendPhone()
        is Action.ChangeCurrentZip -> viewModel.setZip(action.zip)
        is Action.ChangePhone -> viewModel.changePhone(
            action.phone,
            action.lengthPhone,
            action.countryPhoneCode
        )

        is Action.ChangeCode -> viewModel.changeCode(action.code)
    }
}

private sealed interface Action {
    data class Authorization(val phone: String, val code: String) : Action
    data object SendPhone : Action
    data class ChangePhone(val phone: String, val countryPhoneCode: String, val lengthPhone: Int?) :
        Action

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