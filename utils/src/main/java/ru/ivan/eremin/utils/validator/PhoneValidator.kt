package ru.ivan.eremin.utils.validator

import android.util.Patterns
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import ru.ivan.eremin.utils.R


const val MIN_LENGTH_PHONE = 10
private const val REGEX_NOT_NUM = "\\D+"

fun String.isPhone(minLengthPhone: Int?, countryPhoneCode: String): PhoneValidationError? {
    return when {
        this == countryPhoneCode || minLengthPhone == 0 || countryPhoneCode.isBlank() -> null

        isEmpty() || replace(
            REGEX_NOT_NUM.toRegex(),
            ""
        ).length < (minLengthPhone ?: MIN_LENGTH_PHONE) -> PhoneValidationError.Length(
            minLengthPhone ?: 0
        )

        !Patterns.PHONE.matcher(this).matches() -> PhoneValidationError.InvalidCharacters
        else -> null
    }
}

@Immutable
sealed interface PhoneValidationError : ValidatorResult {

    @Immutable
    data class Length(val length: Int) : PhoneValidationError


    @Immutable
    data object InvalidCharacters : PhoneValidationError

    @Composable
    @ReadOnlyComposable
    override fun getErrorText(): String {
        return when (this) {
            is Length -> stringResource(R.string.validator_phone_length_error, length)
            InvalidCharacters -> stringResource(R.string.validator_phone_incorrect)
        }
    }
}
