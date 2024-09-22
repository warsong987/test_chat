package ru.ivan.eremin.registration.impl.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.ivan.eremin.registration.impl.repository.RegistrationRepositoryImpl
import ru.ivan.eremin.registration.repository.RegistrationRepository

val registrationModule = module {
    singleOf(::RegistrationRepositoryImpl) bind RegistrationRepository::class
}