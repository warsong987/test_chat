package ru.ivan.eremin.testchat.presentation.screen.authorization

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.ivan.eremin.testchat.domain.authorization.usecase.authorization.CheckAuthCodeUseCase
import ru.ivan.eremin.testchat.domain.authorization.usecase.authorization.SendPhoneForGetSmsCodeUseCase
import ru.ivan.eremin.testchat.presentation.core.BaseViewModel
import ru.ivan.eremin.testchat.presentation.core.ErrorHandler.uiErrorHandle
import ru.ivan.eremin.testchat.presentation.utils.validator.isPhone
import ru.ivan.eremin.testchat.presentation.utils.validator.isSmsCode
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val sendPhoneForGetSmsCodeUseCase: SendPhoneForGetSmsCodeUseCase,
    private val checkAuthCodeUseCase: CheckAuthCodeUseCase
) : BaseViewModel<AuthorizationUiState>() {
    override fun createInitialState(): AuthorizationUiState {
        return AuthorizationUiState()
    }

    fun sendPhone(phone: String) {
        viewModelScope.launch {
            try {
                val isSuccess = sendPhoneForGetSmsCodeUseCase(phone)
                updateState {
                    copy(
                        isSuccessSendPhone = isSuccess
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

    fun checkCode(phone: String, code: String) {
        viewModelScope.launch {
            try {
                val userIsExist = checkAuthCodeUseCase(phone, code)
                updateState {
                    copy(
                        events = events + AuthorizationEvent.RegistrationIsSuccess(userIsExist, phone)
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

    fun changePhone(phone: String) {
        updateState {
            copy(
                phone = phone,
                errorPhone = phone.isPhone(count)
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

    private var count = 0

    fun changeCount(count: Int) {
        this.count = count
        updateState {
            copy(
                phone = phone,
                errorPhone = phone?.isPhone(count)
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