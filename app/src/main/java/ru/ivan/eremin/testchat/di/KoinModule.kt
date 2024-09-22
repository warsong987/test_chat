package ru.ivan.eremin.testchat.di

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.ivan.chat.shared.sharedModule
import ru.ivan.eremin.chat.auth.ui.di.authUiModule
import ru.ivan.eremin.chat.chat.di.chatUiModule
import ru.ivan.eremin.chat.config.AppConfig
import ru.ivan.eremin.chat.config.NetworkConfig
import ru.ivan.eremin.chat.profile.di.profileUiModule
import ru.ivan.eremin.chat.registration.di.registrationUiModule

fun startKoinApp(context: Context) {
    val configurationModule = module {
        single<NetworkConfig> {
            NetworkConfig(
                "",
                "",
                0,
                0,
            )
        }
        single<AppConfig> {
            AppConfig(
                "",
                0,
                "",
                CoroutineScope(SupervisorJob() + Dispatchers.IO),
                CoroutineScope(SupervisorJob() + Dispatchers.Main),
                Dispatchers.IO,
                Dispatchers.Default,
            )
        }
    }

    startKoin {
        androidContext(context)
        modules(configurationModule)
        modules(sharedModule)
        modules(authUiModule)
        modules(registrationUiModule)
        modules(profileUiModule)
        modules(chatUiModule)
    }
}
