package ru.ivan.eremin.testchat.data.entity.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginOutResponse(
    @SerialName("refresh_token")
    val refreshToken: String?,
    @SerialName("access_token")
    val accessToken: String?,
    @SerialName("user_id")
    val userId: Long?,
    @SerialName("is_user_exists")
    val isUserExists: Boolean?
)