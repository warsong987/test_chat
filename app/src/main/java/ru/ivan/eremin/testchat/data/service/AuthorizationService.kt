package ru.ivan.eremin.testchat.data.service

import retrofit2.http.Body
import retrofit2.http.POST
import ru.ivan.eremin.testchat.data.entity.auth.CheckAuthCodeRequest
import ru.ivan.eremin.testchat.data.entity.auth.LoginOutResponse
import ru.ivan.eremin.testchat.data.entity.auth.PhoneBaseRequest
import ru.ivan.eremin.testchat.data.entity.auth.SuccessResponse

interface AuthorizationService {
    @POST("users/send-auth-code/")
    suspend fun sendAuthCode(
        @Body phone: PhoneBaseRequest
    ): SuccessResponse

    @POST("users/check-auth-code/")
    suspend fun checkAuthCode(
        @Body phone: CheckAuthCodeRequest
    ): LoginOutResponse
}