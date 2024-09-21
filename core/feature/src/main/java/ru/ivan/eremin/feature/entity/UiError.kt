package ru.ivan.eremin.feature.entity

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import ru.ivan.eremin.feature.R

@Immutable
sealed class UiError {
    data object Network : UiError()
    data object LogOut : UiError()
    data object Unknown : UiError()
    data class Server(val text: String, val code: String = "") : UiError()
}

fun UiError.getErrorMessage(context: Context): String {
    return when (this) {
        is UiError.Network -> context.getString(R.string.connection_failed)
        is UiError.Unknown -> context.getString(R.string.unknown_error)
        is UiError.Server -> text.ifBlank { context.getString(R.string.unknown_error) }
        else -> ""
    }
}

@Composable
@ReadOnlyComposable
fun UiError?.getErrorMessage(): String? {
    return this?.getErrorMessage(LocalContext.current)
}
