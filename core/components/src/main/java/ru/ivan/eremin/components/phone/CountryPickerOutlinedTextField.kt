package ru.ivan.eremin.components.phone

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.components.phone.data.CountryDetails
import ru.ivan.eremin.components.phone.data.CountryPickerProperties
import ru.ivan.eremin.components.phone.data.Dimensions

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun CountryPickerOutlinedTextField(
    mobileNumber: TextFieldValue,
    onMobileNumberChange: (TextFieldValue) -> Unit,
    onCountrySelected: (country: CountryDetails) -> Unit,
    modifier: Modifier = Modifier,
    defaultPaddingValues: PaddingValues = PaddingValues(4.dp, 6.dp),
    countryPickerProperties: CountryPickerProperties = CountryPickerProperties(),
    countryFlagDimensions: Dimensions = Dimensions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Phone
    ),
    country: CountryDetails? = null,
    defaultCountryCode: String?,
    countriesList: List<String>? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLine: Int = 1,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    onDone: () -> Unit = {}
) {
    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        OutlinedTextField(
            enabled = enabled,
            textStyle = textStyle,
            value = mobileNumber,
            onValueChange = onMobileNumberChange,
            modifier = modifier,
            readOnly = readOnly,
            label = label,
            placeholder = placeholder,
            leadingIcon = {
                CountryPicker(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    defaultPaddingValues = defaultPaddingValues,
                    countryPickerProperties = countryPickerProperties,
                    countryFlagDimensions = countryFlagDimensions,
                    defaultCountryCode = defaultCountryCode,
                    country = country,
                    countriesList = countriesList
                ) {
                    onCountrySelected(it)
                }
            },
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = supportingText,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                }
            ),
            maxLines = maxLines,
            minLines = minLine,
            shape = shape,
            isError = isError
        )
    }
}