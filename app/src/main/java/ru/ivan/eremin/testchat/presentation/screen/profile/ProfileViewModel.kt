package ru.ivan.eremin.testchat.presentation.screen.profile

import ru.ivan.eremin.feature.base.BaseViewModel

class ProfileViewModel : BaseViewModel<ProfileUiState>() {
    override fun createInitialState(): ProfileUiState {
        return ProfileUiState()
    }
}