package ru.ivan.eremin.testchat.domain.authorization.usecase.authorization

import ru.ivan.eremin.testchat.domain.authorization.repository.AuthorizationRepository
import javax.inject.Inject

class CheckAuthCodeUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(phone: String, code: String) = repository.checkAuthCode(phone, code)
}