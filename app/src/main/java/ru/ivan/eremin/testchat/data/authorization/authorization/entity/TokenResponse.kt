package ru.ivan.eremin.testchat.data.authorization.authorization.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("refresh_token")
    val refreshToken: String?,
    @SerialName("access_token")
    val accessToken: String?,
    @SerialName("user_id")
    val userId: Int?
)
