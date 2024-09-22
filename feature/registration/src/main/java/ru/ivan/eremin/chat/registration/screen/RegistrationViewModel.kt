package ru.ivan.eremin.chat.registration.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.ivan.eremin.feature.base.BaseViewModel
import ru.ivan.eremin.navigate.Registration

class RegistrationViewModel(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<RegistrationUiState>() {

    private val phone by lazy { savedStateHandle.toRoute<Registration>().phone }

    override fun createInitialState(): RegistrationUiState {
        return RegistrationUiState(
            phone = phone
        )
    }

    private var job: Job? = null


    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {

        }
    }

    fun changeName(name: String) {
        updateState {
            copy(
                name = name
            )
        }
    }

    fun changeUserName(userName: String) {
        updateState {
            copy(
                username = userName
            )
        }
    }

    fun registration(phone: String, name: String, userName: String) {

    }
}