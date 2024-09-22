package ru.ivan.chat.shared

import org.koin.dsl.module
import ru.ivan.eremin.auth.impl.di.authModule
import ru.ivan.eremin.chat.impl.di.chatModule
import ru.ivan.eremin.chat.network.di.networkModule
import ru.ivan.eremin.chat.platform.di.platformModule
import ru.ivan.eremin.chat.profile.impl.di.profileModule
import ru.ivan.eremin.registration.impl.di.registrationModule

val sharedModule = module {
    includes(
        platformModule,
        networkModule,
        authModule,
        registrationModule,
        profileModule,
        chatModule
    )
}