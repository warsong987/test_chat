package ru.ivan.eremin.testchat.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ServiceError(
    @SerialName("msg")
    val msg: String?,
    @SerialName("type")
    val type: String?
) {
    @Serializable
    data class Location(
        @SerialName("title")
        val title: String?
    )
}
