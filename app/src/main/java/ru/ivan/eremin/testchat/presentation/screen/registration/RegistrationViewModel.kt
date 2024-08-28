package ru.ivan.eremin.testchat.presentation.screen.registration

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.ivan.eremin.testchat.presentation.core.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
) : BaseViewModel<RegistrationUiState>() {
    override fun createInitialState(): RegistrationUiState {
        return RegistrationUiState()
    }

    private var job: Job? = null


    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {

        }
    }

    fun registration(phone: String, name: String, userName: String) {

    }
}