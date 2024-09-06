package ru.ivan.eremin.testchat.domain.authorization.usecase.authorization

import ru.ivan.eremin.testchat.domain.authorization.authorization.AuthorizationRepository
import javax.inject.Inject

class CheckAuthCodeUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(phone: String, code: String): Boolean =
        repository.checkAuthCode(phone, code)
}