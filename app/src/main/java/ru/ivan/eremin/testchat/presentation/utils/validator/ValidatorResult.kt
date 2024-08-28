package ru.ivan.eremin.testchat.presentation.utils.validator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable

@Immutable
interface ValidatorResult {

    @Composable
    @ReadOnlyComposable
    fun getErrorText(): String
}