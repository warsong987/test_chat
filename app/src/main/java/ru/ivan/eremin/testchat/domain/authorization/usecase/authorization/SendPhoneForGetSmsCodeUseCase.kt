package ru.ivan.eremin.testchat.domain.authorization.usecase.authorization

import ru.ivan.eremin.testchat.domain.authorization.repository.AuthorizationRepository
import javax.inject.Inject

class SendPhoneForGetSmsCodeUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(phone: String) = repository.sendPhoneForGetCode(phone)
}