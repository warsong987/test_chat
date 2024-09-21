package ru.ivan.eremin.chat.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

data class AppConfig(
    val versionName: String,
    val versionCode: Int,
    val applicationId: String,
    val backgroundScope: CoroutineScope,
    val mainScope: CoroutineScope,
    val dispatcherIO: CoroutineDispatcher,
    val dispatcherDefault: CoroutineDispatcher
)