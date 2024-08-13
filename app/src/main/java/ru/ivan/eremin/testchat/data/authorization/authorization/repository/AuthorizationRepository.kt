package ru.ivan.eremin.testchat.data.authorization.authorization.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import ru.ivan.eremin.testchat.data.authorization.authorization.entity.RegisterInRequest
import ru.ivan.eremin.testchat.data.authorization.authorization.service.AuthorizationService
import ru.ivan.eremin.testchat.domain.authorization.repository.AuthorizationRepository
import javax.inject.Inject


class AuthorizationRepositoryImpl @Inject constructor(
    private val service: AuthorizationService
) : AuthorizationRepository {
    override suspend fun registration(phone: String, name: String, username: String) {
        withContext(Dispatchers.IO) {
            service.register(
                Json.encodeToString(
                    RegisterInRequest(
                        phone,
                        name,
                        username
                    )
                ).toRequestBody("application/json".toMediaTypeOrNull())
            )
        }
    }
}