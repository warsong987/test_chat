package ru.ivan.eremin.utils.validator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import androidx.core.text.isDigitsOnly
import ru.ivan.eremin.utils.R

const val LENGTH_CODE = 6

fun String.isSmsCode(): CodeValidatorError? {
    return when {
        isEmpty() || this.length < LENGTH_CODE
                || this.length > LENGTH_CODE
        -> CodeValidatorError.Length

        !this.isDigitsOnly() -> CodeValidatorError.InvalidCharacters
        else -> null
    }
}

@Immutable
sealed interface CodeValidatorError : ValidatorResult {

    @Immutable
    data object Length : CodeValidatorError

    @Immutable
    data object InvalidCharacters : CodeValidatorError


    @Composable
    @ReadOnlyComposable
    override fun getErrorText(): String {
        return when (this) {
            Length -> stringResource(R.string.validator_code_length_error)
            InvalidCharacters -> stringResource(R.string.validator_code_incorrect)
        }
    }
}