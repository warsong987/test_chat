package ru.ivan.eremin.testchat.presentation.navigate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.ivan.eremin.testchat.R

sealed class Routes(
    protected val route: String,
    @StringRes val resourceId: Int? = null,
    @DrawableRes val iconResourceId: Int? = null,
    vararg params: String
) {
    val fullRouter: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }
}

data object Authorization : Routes("authorization")

data object Registration : Routes("registration") {
    private const val PHONE = "phone"

    operator fun invoke(phone: String) = route.appendParams(
        PHONE to phone
    )
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let {
            builder.append("/$it")
        }
    }

    return builder.toString()
}

data object Chats : Routes("chats", R.string.chats, R.drawable.ic_chat)
data object Profile : Routes("profiles", R.string.profile, R.drawable.ic_profile)


val itemsBottomBar = listOf(
    Chats,
    Profile
)