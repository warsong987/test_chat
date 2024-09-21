package ru.ivan.eremin.testchat.presentation.screen.profile

import ru.ivan.eremin.feature.entity.UiError
import ru.ivan.eremin.feature.entity.UiState
import ru.ivan.eremin.testchat.domain.profile.entity.Profile

data class ProfileUiState(
    val skeleton: Boolean = false,
    val refresh: Boolean = false,
    val error: UiError? = null,
    val data: Profile? = null
): UiState