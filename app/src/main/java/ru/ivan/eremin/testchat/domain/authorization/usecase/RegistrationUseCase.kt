package ru.ivan.eremin.testchat.domain.authorization.usecase

import ru.ivan.eremin.testchat.domain.authorization.repository.AuthorizationRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(
        phone: String,
        name: String,
        username: String
    ) {
        repository.registration(phone, name, username)
    }
}