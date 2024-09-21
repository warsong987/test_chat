package ru.ivan.eremin.chat.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.CacheStorage
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.ContentTypeMatcher
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.json.Json
import ru.ivan.eremin.chat.config.AppConfig
import ru.ivan.eremin.chat.config.NetworkConfig
import ru.ivan.eremin.chat.config.TokenHandler
import ru.ivan.eremin.chat.network.mapping.ExceptionMapper.mapToAppException
import ru.ivan.eremin.chat.network.plugins.AppInfo
import ru.ivan.eremin.chat.network.plugins.Authenticator
import ru.ivan.eremin.chat.network.plugins.CustomTimeout

fun getDefaultClient(
    networkConfiguration: NetworkConfig,
    appConfiguration: AppConfig,
    cache: CacheStorage?
): HttpClient {
    val jsonClient = Json {
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = false
    }
    return HttpClient {
        install(HttpCache) {
            cache?.let {
                publicStorage(it)
            }
        }
        install(UserAgent) {
            agent = networkConfiguration.userAgent
        }
        val matcher = object : ContentTypeMatcher {
            override fun contains(contentType: ContentType): Boolean {
                return true
            }
        }
        install(ContentNegotiation) {
            register(
                contentTypeToSend = ContentType.Application.Json,
                converter = KotlinxSerializationConverter(jsonClient),
                contentTypeMatcher = matcher,
                configuration = {}
            )
        }
        install(HttpRedirect)
        install(HttpTimeout) {
            socketTimeoutMillis = networkConfiguration.socketTimeout
            connectTimeoutMillis = networkConfiguration.connectTimeout
        }
        install(CustomTimeout)
        install(HttpRequestRetry) {
            retryIf(REFRESH_REQUEST_COUNT) { _, response ->
                response.status.value.let { it in STATUS_5XX || it == STATUS_423 } &&
                        response.request.method in listOf(HttpMethod.Get)
            }
            exponentialDelay()
        }
        install(ContentEncoding) {
            gzip()
        }
        install(AppInfo) {
            appVersion = appConfiguration.versionName
        }
        expectSuccess = true
        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, request ->
                throw exception.mapToAppException()
            }
        }
    }
}

fun getAuthHttpClient(
    httpClient: HttpClient,
    tokenHandler: TokenHandler
) = httpClient.config {
    install(Authenticator) {
        loadToken = {
            tokenHandler.getToken(login)
        }
        refreshToken = {
            tokenHandler.getToken(login, oldToken)
        }
    }
}

private const val REFRESH_REQUEST_COUNT = 5
private val STATUS_5XX = 500..599
private const val STATUS_423 = 423