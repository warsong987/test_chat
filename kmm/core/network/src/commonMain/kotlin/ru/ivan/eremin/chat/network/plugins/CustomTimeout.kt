package ru.ivan.eremin.chat.network.plugins

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.timeout
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.util.AttributeKey
import io.ktor.util.KtorDsl
import ru.ivan.eremin.chat.network.entity.TAG_TIMEOUT

internal class CustomTimeout private constructor() {

    @KtorDsl
    class Config

    companion object Plugin: HttpClientPlugin<Config, CustomTimeout> {
        override val key: AttributeKey<CustomTimeout> = AttributeKey("CustomTimeout")

        override fun prepare(block: Config.() -> Unit) = CustomTimeout()

        override fun install(plugin: CustomTimeout, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                context.attributes.getOrNull(TIMEOUT)?.let { customTimeout->
                    context.timeout {
                        socketTimeoutMillis = customTimeout * SECOND
                    }
                }
            }
        }

    }
}

private val TIMEOUT = AttributeKey<Int>(TAG_TIMEOUT)
private const val SECOND = 1000L