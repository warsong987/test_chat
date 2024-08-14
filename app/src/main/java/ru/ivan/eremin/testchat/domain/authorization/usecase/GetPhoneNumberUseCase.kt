package ru.ivan.eremin.testchat.domain.authorization.usecase

import ru.ivan.eremin.testchat.domain.authorization.repository.AuthorizationRepository
import javax.inject.Inject

class GetPhoneNumberUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke() = repository.getCurrentPhoneNumber()
}