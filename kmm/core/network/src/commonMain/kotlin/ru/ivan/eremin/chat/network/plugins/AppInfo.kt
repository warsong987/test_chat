package ru.ivan.eremin.chat.network.plugins

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.header
import io.ktor.util.AttributeKey
import io.ktor.util.KtorDsl
import ru.ivan.eremin.chat.network.entity.HEADER_APP_VERSION

internal class AppInfo private constructor(internal val appVersion: String?){

    @KtorDsl
    class Config(var appVersion: String? = null)

    companion object Plugin : HttpClientPlugin<Config, AppInfo> {
        override val key: AttributeKey<AppInfo> = AttributeKey("AppInfo")

        override fun prepare(block: Config.() -> Unit) = AppInfo(Config().appVersion)

        override fun install(plugin: AppInfo, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                plugin.appVersion?.let {
                    context.header(HEADER_APP_VERSION, it)
                }
            }
        }

    }
}