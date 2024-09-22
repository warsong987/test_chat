package ru.ivan.eremin.chat.impl.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.ivan.eremin.chat.api.repository.ChatRepository
import ru.ivan.eremin.chat.impl.repository.ChatRepositoryImpl

val chatModule = module {
    singleOf(::ChatRepositoryImpl) bind ChatRepository::class
}