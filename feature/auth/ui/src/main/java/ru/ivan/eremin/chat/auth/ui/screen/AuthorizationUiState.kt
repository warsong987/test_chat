package ru.ivan.eremin.chat.auth.ui.screen

import ru.ivan.eremin.feature.entity.UiError
import ru.ivan.eremin.feature.entity.UiEvent
import ru.ivan.eremin.feature.entity.UiState
import ru.ivan.eremin.utils.validator.CodeValidatorError
import ru.ivan.eremin.utils.validator.PhoneValidationError

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
        get() = errorPhone == null && !phone.isNullOrEmpty() && codeError == null && !code.isNullOrEmpty()
}

sealed interface AuthorizationEvent : UiEvent {
    data class Authorization(val userIsExist: Boolean, val phone: String) : AuthorizationEvent
    data class OpenRegistration(val phone: String) : AuthorizationEvent
}