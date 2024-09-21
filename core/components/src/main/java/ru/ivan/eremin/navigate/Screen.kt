package ru.ivan.eremin.navigate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable
import ru.ivan.eremin.components.R

@Serializable
sealed class Routes(
    @StringRes val resourceId: Int? = null,
    @DrawableRes val iconResourceId: Int? = null
)

@Serializable
data object Authorization : Routes()

@Serializable
data class Registration(val phone: String) : Routes()

@Serializable
data object Chats : Routes(R.string.chats, R.drawable.ic_chat)

@Serializable
data object Profile : Routes(R.string.profile, R.drawable.ic_profile)

@Serializable
data class Chat(val id: Int) : Routes()


val itemsBottomBar = listOf(
    Chats,
    Profile
)