package ru.ivan.eremin.testchat.domain.authorization.usecase.authorization

import ru.ivan.eremin.testchat.domain.authorization.authorization.AuthorizationRepository
import javax.inject.Inject

class SendAuthCodeUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(phoneNumber: String): Boolean {
        return repository.sendAuthCode(phoneNumber)
    }
}