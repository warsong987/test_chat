package ru.ivan.eremin.testchat.presentation.navigate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.ivan.eremin.testchat.R

sealed class Routes(
    val route: String,
    @StringRes val resourceId: Int? = null,
    @DrawableRes val iconResourceId: Int? = null
)

data object Authorization : Routes("authorization")
data object Registration : Routes("registration")
data object Chats : Routes("chats", R.string.chats, R.drawable.ic_chat)
data object Profile : Routes("profiles", R.string.profile, R.drawable.ic_profile)


val itemsBottomBar = listOf(
    Chats,
    Profile
)