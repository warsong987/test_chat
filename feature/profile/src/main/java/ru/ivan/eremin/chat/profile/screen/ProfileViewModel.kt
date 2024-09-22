package ru.ivan.eremin.chat.profile.screen

import ru.ivan.eremin.feature.base.BaseViewModel

class ProfileViewModel : BaseViewModel<ProfileUiState>() {
    override fun createInitialState(): ProfileUiState {
        return ProfileUiState()
    }
}