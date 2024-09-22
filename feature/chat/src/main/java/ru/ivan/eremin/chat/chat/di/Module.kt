package ru.ivan.eremin.chat.chat.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.ivan.eremin.chat.chat.screen.chat.ChatViewModel
import ru.ivan.eremin.chat.chat.screen.chats.ChatsViewModel

val chatUiModule = module {
    viewModelOf(::ChatViewModel)
    viewModelOf(::ChatsViewModel)
}