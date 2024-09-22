package ru.ivan.eremin.chat.profile.impl.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.ivan.eremin.chat.profile.impl.repository.ProfileRepositoryImpl
import ru.ivan.eremin.chat.profile.repository.ProfileRepository

val profileModule = module {
    singleOf(::ProfileRepositoryImpl) bind ProfileRepository::class
}