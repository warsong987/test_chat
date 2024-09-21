package ru.ivan.eremin.chat.platform.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.ivan.eremin.chat.platform.network.NetworkUtils
import ru.ivan.eremin.chat.platform.network.NetworkUtilsImpl

actual val platformModule: Module = module {
  singleOf(::NetworkUtilsImpl) bind NetworkUtils::class
}