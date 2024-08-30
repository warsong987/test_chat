package ru.ivan.eremin.testchat.presentation.utils.validator

import android.util.Patterns
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import ru.ivan.eremin.testchat.R


const val MIN_LENGTH_PHONE = 10
private const val REGEX_NOT_NUM = "\\D+"

fun String.isPhone(): PhoneValidationError? {
    return when {
        isEmpty() || replace(
            REGEX_NOT_NUM.toRegex(),
            ""
        ).length < MIN_LENGTH_PHONE -> PhoneValidationError.Length

        !Patterns.PHONE.matcher(this).matches() -> PhoneValidationError.InvalidCharacters
        else -> null
    }
}

@Immutable
sealed interface PhoneValidationError : ValidatorResult {

    @Immutable
    data object Length : PhoneValidationError


    @Immutable
    data object InvalidCharacters : PhoneValidationError

    @Composable
    @ReadOnlyComposable
    override fun getErrorText(): String {
        return when (this) {
            Length -> stringResource(R.string.validator_phone_length_error)
            InvalidCharacters -> stringResource(R.string.validator_phone_incorrect)
        }
    }
}
