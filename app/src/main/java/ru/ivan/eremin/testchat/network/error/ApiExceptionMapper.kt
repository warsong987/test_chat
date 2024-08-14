package ru.ivan.eremin.testchat.network.error

import android.net.Uri
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import ru.ivan.eremin.testchat.network.entity.ServiceError
import ru.ivan.eremin.testchat.network.exception.ServerException
import javax.inject.Inject

class ApiExceptionMapper @Inject constructor(private val json: Json) :
    HttpExceptionMapper(emptyList()) {

    override fun map(httpException: HttpException): Exception {
        val response = httpException.response()
        val request = response?.raw()?.request
        val error = try {
            json.decodeFromString<ServiceError>(response?.errorBody()?.string() ?: "")
        } catch (_: Exception) {
            null
        }
        return ServerException(
            error?.msg ?: "",
            error?.type ?: "",
            request?.url?.toString()?.let { Uri.parse(it) },
            response?.code()
        )
    }
}