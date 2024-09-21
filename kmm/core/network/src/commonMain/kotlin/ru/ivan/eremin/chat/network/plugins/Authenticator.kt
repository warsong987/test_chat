package ru.ivan.eremin.chat.network.plugins

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.header
import io.ktor.http.HttpStatusCode
import io.ktor.util.AttributeKey
import io.ktor.util.KtorDsl
import ru.ivan.eremin.chat.network.entity.HEADER_AUTHORIZATION
import ru.ivan.eremin.chat.network.entity.HEADER_DOMAIN
import ru.ivan.eremin.chat.network.entity.HEADER_LOGIN
import token.AccessTokenData

internal class Authenticator private constructor(
    internal val getToken: suspend TokenParams.() -> AccessTokenData?,
    internal val refreshToken: suspend RefreshTokenParams.() -> AccessTokenData?
) {
    @KtorDsl
    class Config(
        var loadToken: suspend TokenParams.() -> AccessTokenData? = { null },
        var refreshToken: suspend RefreshTokenParams.() -> AccessTokenData? = { null }
    )

    companion object Plugin : HttpClientPlugin<Config, Authenticator> {
        override val key: AttributeKey<Authenticator> = AttributeKey("Authenticator")

        override fun prepare(block: Config.() -> Unit): Authenticator {
            return Config().apply(block).let { Authenticator(it.loadToken, it.refreshToken) }
        }

        override fun install(plugin: Authenticator, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                val login = context.headers[HEADER_LOGIN].orEmpty()
                if (context.headers.contains(HEADER_DOMAIN) && !context.headers.contains(
                        HEADER_AUTHORIZATION
                    )
                ) {
                    val token = plugin.getToken(TokenParams(login))
                    context.apply {
                        headers.remove(HEADER_LOGIN)
                        header(HEADER_LOGIN, "$BEARER ${token?.accessToken}")
                        attributes.put(TOKEN, token?.accessToken.orEmpty())
                    }
                }
            }
            scope.plugin(HttpSend).intercept { originalRequest ->
                val login = originalRequest.headers[HEADER_LOGIN]
                val originalCall = execute(originalRequest)
                val needRefresh =
                    originalCall.response.status == HttpStatusCode.Unauthorized && !login.isNullOrEmpty()
                if (needRefresh) {
                    val currentToken = originalRequest.attributes[TOKEN]
                    val newToken = plugin.refreshToken(
                        RefreshTokenParams(login.orEmpty(), currentToken)
                    )
                    val request = originalRequest.apply {
                        headers.remove(HEADER_AUTHORIZATION)
                        header(HEADER_AUTHORIZATION, "$BEARER ${newToken?.accessToken}")
                    }
                    execute(request)
                } else {
                    originalCall
                }
            }
        }

    }
}

data class TokenParams(
    val login: String
)

data class RefreshTokenParams(
    val login: String,
    val oldToken: String
)

private const val BEARER = "Bearer"
private val TOKEN = AttributeKey<String>("Token")