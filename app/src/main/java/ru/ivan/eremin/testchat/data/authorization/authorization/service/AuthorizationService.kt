package ru.ivan.eremin.testchat.data.authorization.authorization.service

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import ru.ivan.eremin.testchat.data.authorization.authorization.entity.TokenResponse

interface AuthorizationService {

    @POST("api/v1/user/register")
    suspend fun register(
        @Body requestBody: RequestBody
    ): TokenResponse
}