package ru.ivan.eremin.testchat.data.entity.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponse(
    @SerialName("is_success")
    val isSuccess: Boolean?,
)