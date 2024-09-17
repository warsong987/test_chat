package ru.ivan.eremin.testchat.presentation.screen.registration

import ru.ivan.eremin.testchat.presentation.core.UiError
import ru.ivan.eremin.testchat.presentation.core.UiState

data class RegistrationUiState (
    val skeleton: Boolean = true,
    val refresh: Boolean = false,
    val phone: String? = null,
    val name: String? = null,
    val username: String? = null,
    val error: UiError? = null
): UiState