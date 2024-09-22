package ru.ivan.eremin.chat.registration.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.ivan.eremin.chat.registration.screen.RegistrationViewModel

val registrationUiModule = module {
    viewModelOf(::RegistrationViewModel)
}