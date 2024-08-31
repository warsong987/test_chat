package ru.ivan.eremin.testchat.presentation.screen.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.ivan.eremin.testchat.presentation.core.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel<ProfileUiState>() {
    override fun createInitialState(): ProfileUiState {
        return ProfileUiState()
    }
}