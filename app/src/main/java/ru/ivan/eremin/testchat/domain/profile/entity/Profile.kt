package ru.ivan.eremin.testchat.domain.profile.entity

import androidx.compose.runtime.Immutable

@Immutable
data class Profile(
    val name: String,
    val userName: String,
    val birthday: String,
    val city: String,
    val instagram: String,
    val status: String,
    val avatar: String,
    val id: String,
    val last: String,
    val online: Boolean,
    val created: String,
    val phone: String,
    val completedTask: Int,
    val avatars: Avatar,
) {
    @Immutable
    data class Avatar(
        val avatar: String,
        val bigAvatar: String,
        val miniAvatar: String,
    )
}