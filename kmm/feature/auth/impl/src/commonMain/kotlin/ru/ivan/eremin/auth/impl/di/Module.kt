package ru.ivan.eremin.auth.impl.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.ivan.eremin.auth.impl.repository.AuthRepositoryImpl
import ru.ivan.eremin.auth.repository.AuthRepository

val authModule = module {
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
}