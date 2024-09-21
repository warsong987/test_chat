package ru.ivan.eremin.chat.auth.ui.screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ivan.eremin.auth.repository.AuthRepository
import ru.ivan.eremin.feature.base.BaseViewModel
import ru.ivan.eremin.feature.utils.ErrorHandler.uiErrorHandle
import ru.ivan.eremin.utils.validator.isPhone
import ru.ivan.eremin.utils.validator.isSmsCode

class AuthorizationViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel<AuthorizationUiState>() {
    override fun createInitialState(): AuthorizationUiState {
        return AuthorizationUiState()
    }

    fun sendPhone() {
        state.value.phone?.let { phone ->
            viewModelScope.launch {
                try {
                    val filtredPhone = phone.filter { it.isDigit() }
                    val isSuccess = authRepository.sendAuthCode(filtredPhone)
                    if (isSuccess) {
                        updateState {
                            copy(
                                isSuccessSendPhone = true,
                            )
                        }
                    } else {
                        updateState {
                            copy(
                                events = events + AuthorizationEvent.OpenRegistration(phone)
                            )
                        }
                    }
                } catch (e: Exception) {
                    updateState {
                        copy(
                            events = events + AuthorizationEvent.OpenRegistration(phone)
                        )
                    }
                }
            }
        }
    }

    fun checkCode(phone: String, code: String) {
        viewModelScope.launch {
            updateState {
                copy(
                    events = events + AuthorizationEvent.Authorization(true, code)
                )
            }
            try {
                val userIsExist = authRepository.checkAuthCode(phone, code)
                updateState {
                    copy(
                        events = events + AuthorizationEvent.Authorization(userIsExist, phone)
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

    fun setZip(zip: String) {
        updateState {
            copy(
                phoneZip = zip
            )
        }
    }

    fun changePhone(phone: String, lengthPhone: Int?, countryPhoneCode: String) {
        updateState {
            copy(
                phone = phone,
                errorPhone = phone.isPhone(lengthPhone, countryPhoneCode)
            )
        }
    }

    fun changeCode(code: String) {
        updateState {
            copy(
                code = code,
                codeError = code.isSmsCode()
            )
        }
    }

    fun removeEvent(event: AuthorizationEvent) {
        updateState {
            copy(
                events = events - event
            )
        }
    }
}