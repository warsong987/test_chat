package ru.ivan.eremin.testchat.presentation.screen.authorization

import ru.ivan.eremin.testchat.presentation.core.UiError
import ru.ivan.eremin.testchat.presentation.core.UiEvent
import ru.ivan.eremin.testchat.presentation.core.UiState
import ru.ivan.eremin.testchat.presentation.utils.validator.CodeValidatorError
import ru.ivan.eremin.testchat.presentation.utils.validator.PhoneValidationError

data class AuthorizationUiState(
    val isProgress: Boolean = false,
    val isSuccessSendPhone: Boolean = false,
    val phone: String? = null,
    val phoneZip: String? = null,
    val errorPhone: PhoneValidationError? = null,
    val code: String? = null,
    val codeError: CodeValidatorError? = null,
    val error: UiError? = null,
    val events: List<AuthorizationEvent> = emptyList()
) : UiState {
    val isActionSendPhone: Boolean
        get() = errorPhone == null && !phone.isNullOrEmpty()

    val isActionCheckAuthorization: Boolean
        get() = errorPhone == null && phone.isNullOrEmpty() && codeError == null && code.isNullOrEmpty()
}

sealed interface AuthorizationEvent : UiEvent {
    data class RegistrationIsSuccess(val userIsExist: Boolean, val phone: String) : AuthorizationEvent
}