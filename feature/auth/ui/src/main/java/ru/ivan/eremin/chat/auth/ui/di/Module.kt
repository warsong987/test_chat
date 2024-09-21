package ru.ivan.eremin.chat.auth.ui.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.ivan.eremin.chat.auth.ui.screen.AuthorizationViewModel

val authUiModule = module {
    viewModelOf(::AuthorizationViewModel)
}