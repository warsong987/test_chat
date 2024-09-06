package ru.ivan.eremin.testchat.presentation.screen.authorization

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.ivan.eremin.testchat.domain.authorization.usecase.authorization.CheckAuthCodeUseCase
import ru.ivan.eremin.testchat.domain.authorization.usecase.authorization.SendAuthCodeUseCase
import ru.ivan.eremin.testchat.presentation.core.BaseViewModel
import ru.ivan.eremin.testchat.presentation.core.ErrorHandler.uiErrorHandle
import ru.ivan.eremin.testchat.presentation.utils.validator.isPhone
import ru.ivan.eremin.testchat.presentation.utils.validator.isSmsCode
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val sendPhoneUseCase: SendAuthCodeUseCase,
    private val checkAuthCodeUseCase: CheckAuthCodeUseCase
) : BaseViewModel<AuthorizationUiState>() {
    override fun createInitialState(): AuthorizationUiState {
        return AuthorizationUiState()
    }

    fun sendPhone(phone: String) {
        viewModelScope.launch {
            updateState {
                copy(
                    isSuccessSendPhone = true
                )
            }
            try {
                val isSuccess = sendPhoneUseCase(phone)
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
            updateState {
                copy(
                    events = events + AuthorizationEvent.Authorization(true, code)
                )
            }
            try {
                val userIsExist = checkAuthCodeUseCase(phone, code)
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

    fun changePhone(phone: String) {
        updateState {
            copy(
                phone = phone,
                errorPhone = phone.isPhone()
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