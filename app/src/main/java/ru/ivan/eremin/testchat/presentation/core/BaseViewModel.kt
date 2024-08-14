package ru.ivan.eremin.testchat.presentation.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State : UiState> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val uiState: MutableStateFlow<State> by lazy { MutableStateFlow(initialState) }
    val state by lazy { uiState.asStateFlow() }

    protected fun updateState(newState: State.() -> State) {
        uiState.update {
            newState.invoke(it)
        }
    }
}