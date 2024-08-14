package ru.ivan.eremin.testchat.data.authorization.authorization.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterInRequest(
    @SerialName("phone")
    val phone: String,
    @SerialName("name")
    val name: String,
    @SerialName("username")
    val username: String
)