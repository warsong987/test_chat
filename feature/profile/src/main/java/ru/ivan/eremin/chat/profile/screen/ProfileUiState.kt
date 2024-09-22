package ru.ivan.eremin.chat.profile.screen

import ru.ivan.eremin.chat.profile.entity.Profile
import ru.ivan.eremin.feature.entity.UiError
import ru.ivan.eremin.feature.entity.UiState

data class ProfileUiState(
    val skeleton: Boolean = false,
    val refresh: Boolean = false,
    val error: UiError? = null,
    val data: Profile? = null
): UiState