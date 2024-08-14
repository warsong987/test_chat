package ru.ivan.eremin.testchat.presentation.screen.registration

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.ivan.eremin.testchat.domain.authorization.usecase.GetPhoneNumberUseCase
import ru.ivan.eremin.testchat.domain.authorization.usecase.RegistrationUseCase
import ru.ivan.eremin.testchat.presentation.core.BaseViewModel
import ru.ivan.eremin.testchat.presentation.core.ErrorHandler.uiErrorHandle
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase
) : BaseViewModel<RegistrationUiState>() {
    override fun createInitialState(): RegistrationUiState {
        return RegistrationUiState()
    }

    private var job: Job? = null


    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                val phone = getPhoneNumberUseCase()
                updateState {
                    copy(
                        phone = phone
                    )
                }
            } catch (e: Exception) {
                updateState {
                    copy(
                        error = e.uiErrorHandle()
                    )
                }
            }
        }

    }
}