package ru.ivan.eremin.chat.registration.screen

import ru.ivan.eremin.feature.entity.UiError
import ru.ivan.eremin.feature.entity.UiState

data class RegistrationUiState (
    val skeleton: Boolean = true,
    val refresh: Boolean = false,
    val phone: String? = null,
    val name: String? = null,
    val username: String? = null,
    val error: UiError? = null
): UiState