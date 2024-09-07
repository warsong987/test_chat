package ru.ivan.eremin.testchat.data.repository.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ivan.eremin.testchat.data.entity.auth.CheckAuthCodeRequest
import ru.ivan.eremin.testchat.data.entity.auth.PhoneBaseRequest
import ru.ivan.eremin.testchat.data.mapper.auth.AuthMapper.mapToDb
import ru.ivan.eremin.testchat.data.service.auth.AuthorizationService
import ru.ivan.eremin.testchat.database.dao.AuthDao
import ru.ivan.eremin.testchat.domain.authorization.authorization.AuthorizationRepository
import javax.inject.Inject


class AuthorizationRepositoryImpl @Inject constructor(
    private val service: AuthorizationService,
    private val authDao: AuthDao
) : AuthorizationRepository {

    override suspend fun sendAuthCode(phone: String): Boolean {
        return withContext(Dispatchers.IO) {
            service.sendAuthCode(
                PhoneBaseRequest(
                    phone = phone
                )
            ).isSuccess ?: false
        }
    }

    override suspend fun checkAuthCode(phone: String, code: String): Boolean {
        return withContext(Dispatchers.IO) {
            service.checkAuthCode(
                CheckAuthCodeRequest(
                    phone = phone,
                    code = code
                )
            ).also {
                if (it.isUserExists == false) {
                    it.mapToDb()?.let { authDao.insert(it) }
                }
            }.isUserExists ?: false
        }
    }
}