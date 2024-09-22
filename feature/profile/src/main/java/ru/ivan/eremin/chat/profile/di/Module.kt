package ru.ivan.eremin.chat.profile.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.ivan.eremin.chat.profile.screen.ProfileViewModel

val profileUiModule = module {
    viewModelOf(::ProfileViewModel)
}