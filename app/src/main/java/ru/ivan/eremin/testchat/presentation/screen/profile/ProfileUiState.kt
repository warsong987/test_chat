package ru.ivan.eremin.testchat.presentation.screen.profile

import ru.ivan.eremin.testchat.domain.profile.entity.Profile
import ru.ivan.eremin.testchat.presentation.core.UiError
import ru.ivan.eremin.testchat.presentation.core.UiState

data class ProfileUiState(
    val skeleton: Boolean = false,
    val refresh: Boolean = false,
    val error: UiError? = null,
    val data: Profile? = null
): UiState