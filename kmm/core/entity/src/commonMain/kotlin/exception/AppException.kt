package exception

sealed class AppException(message: String? = null, cause: Throwable? = null) : Exception(message, cause )