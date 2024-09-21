package ru.ivan.eremin.utils.validator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable

@Immutable
interface ValidatorResult {

    @Composable
    @ReadOnlyComposable
    fun getErrorText(): String
}