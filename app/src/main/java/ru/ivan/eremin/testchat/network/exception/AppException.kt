package ru.ivan.eremin.testchat.network.exception

import java.io.IOException

sealed class AppException(message: String? = null, cause: Throwable? = null) : IOException(message, cause)