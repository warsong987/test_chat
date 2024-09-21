package ru.ivan.eremin.components

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.staticCompositionLocalOf

val LocalLogOutHandler = staticCompositionLocalOf<LogOutHandler> {
    error("CompositionLocal LocalLogOutHandler not present")
}

interface LogOutHandler {
    fun restartApp()

    companion object {
        const val CLEANED = "clean"
    }
}

class CustomLogOutHandler(private val context: Context) : LogOutHandler {
    override fun restartApp() {
        context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra(LogOutHandler.CLEANED, true)
        }?.let { context.startActivity(it) }
    }
}
