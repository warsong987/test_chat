package ru.ivan.eremin.testchat.presentation.utils.validator

import android.util.Patterns
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import ru.ivan.eremin.testchat.R

fun String.isPhone(count: Int): PhoneValidationError? {
    return when {
        isEmpty() || length < count -> PhoneValidationError.Length(count)

        !Patterns.PHONE.matcher(this).matches() -> PhoneValidationError.InvalidCharacters
        else -> null
    }
}

@Immutable
sealed interface PhoneValidationError : ValidatorResult {

    @Immutable
    data class Length(val count: Int) : PhoneValidationError

    @Immutable
    data object InvalidCharacters : PhoneValidationError


    @Composable
    @ReadOnlyComposable
    override fun getErrorText(): String {
        return when (this) {
            is Length -> stringResource(R.string.validator_phone_length_error, this.count)
            InvalidCharacters -> stringResource(R.string.validator_phone_incorrect)
        }
    }
}