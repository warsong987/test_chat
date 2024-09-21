package ru.ivan.chat.shared

import org.koin.dsl.module
import ru.ivan.eremin.auth.impl.di.authModule
import ru.ivan.eremin.chat.network.di.networkModule
import ru.ivan.eremin.chat.platform.di.platformModule

val sharedModule = module {
    includes(
        platformModule,
        networkModule,
        authModule
    )
}