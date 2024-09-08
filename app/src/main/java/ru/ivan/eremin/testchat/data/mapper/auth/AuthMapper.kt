package ru.ivan.eremin.testchat.data.mapper.auth

import ru.ivan.eremin.testchat.data.entity.auth.LoginOutResponse
import ru.ivan.eremin.testchat.database.entity.AuthDb

object AuthMapper {
    fun LoginOutResponse.mapToDb(): AuthDb? {
        return this.userId?.let {
            AuthDb(
                userId = it,
                refreshToken = this.refreshToken.orEmpty(),
                accessToken = this.accessToken.orEmpty(),
            )
        }
    }
}