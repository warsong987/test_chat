package ru.ivan.eremin.chat.network.di

import com.vipulasri.kachetor.KachetorStorage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cache.storage.CacheStorage
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.ivan.eremin.chat.config.AppConfig
import ru.ivan.eremin.chat.network.HttpClientNaming
import ru.ivan.eremin.chat.network.RequestHelper
import ru.ivan.eremin.chat.network.getAuthHttpClient
import ru.ivan.eremin.chat.network.getDefaultClient

val networkModule = module {
    factory<CacheStorage> { KachetorStorage(CACHE_SIZE) }
    single<HttpClient>(named(HttpClientNaming.UNAUTH)) {
        getDefaultClient(
            get(),
            get(),
            getOrNull()
        )
    }
    single<HttpClient>(named(HttpClientNaming.AUTH)) {
        getAuthHttpClient(
            get(named(HttpClientNaming.UNAUTH)),
            get()
        )
    }

    factory { RequestHelper(get<AppConfig>().backgroundScope, getOrNull()) }

}

private const val CACHE_SIZE = 10 * 1024 * 1024L