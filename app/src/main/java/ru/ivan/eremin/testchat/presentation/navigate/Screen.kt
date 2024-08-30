package ru.ivan.eremin.testchat.presentation.navigate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.ivan.eremin.testchat.R

sealed class Routes(
    val route: String,
    @StringRes val resourceId: Int? = null,
    @DrawableRes val iconResourceId: Int? = null
)

data object AuthorizationRoute : Routes("authorization")
data object RegistrationRoute : Routes("registration")
data object ChatsRoute : Routes("chats", R.string.chats, R.drawable.ic_chat)
data object ProfileRoute : Routes("profiles", R.string.profile, R.drawable.ic_profile)
data object ChatRoute : Routes("chat") {
    const val ID = "id"
}


val itemsBottomBar = listOf(
    ChatsRoute,
    ProfileRoute
)