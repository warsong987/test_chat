package ru.ivan.eremin.testchat.presentation.screen.chats

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.ivan.eremin.testchat.domain.chats.Chat
import ru.ivan.eremin.testchat.presentation.core.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class ChatsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ChatsUiState>() {

    override fun createInitialState(): ChatsUiState {
        return ChatsUiState()
    }

    init {
        updateState {
            copy(
                chats = listOf(
                    Chat(
                        id = 1,
                        name = "Test chat",
                        lastMessage = "Hello, world!",
                        icon = "",
                        persons = listOf(
                            Chat.Person(name = "Person 1"),
                            Chat.Person(name = "Person 2")
                        )
                    ),
                    Chat(
                        id = 2,
                        name = "Test chat 2",
                        lastMessage = "Hello, world!",
                        icon = "",
                        persons = listOf(
                            Chat.Person(name = "Person 1"),
                            Chat.Person(name = "Person 2")
                        )
                    ),
                    Chat(
                        id = 3,
                        name = "Test chat 3",
                        lastMessage = "Hello, world!",
                        icon = "",
                        persons = listOf(
                            Chat.Person(name = "Person 1"),
                            Chat.Person(name = "Person 2")
                        )
                    )
                )
            )
        }
    }
}