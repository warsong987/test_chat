package ru.ivan.eremin.testchat.data.authorization.authorization.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import ru.ivan.eremin.testchat.data.authorization.authorization.entity.RegisterInRequest
import ru.ivan.eremin.testchat.data.authorization.authorization.service.AuthorizationService
import ru.ivan.eremin.testchat.database.dao.AuthDao
import ru.ivan.eremin.testchat.domain.authorization.repository.AuthorizationRepository
import ru.ivan.eremin.testchat.service.phone.PhoneInfo
import javax.inject.Inject


class AuthorizationRepositoryImpl @Inject constructor(
    private val service: AuthorizationService,
    private val authDao: AuthDao,
    private val phoneInfo: PhoneInfo
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

    override suspend fun getCurrentPhoneNumber(): String {
        return withContext(Dispatchers.IO){
            phoneInfo.getPhoneNumber()
        }
    }
}