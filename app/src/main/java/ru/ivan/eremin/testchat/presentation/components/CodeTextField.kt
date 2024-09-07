package ru.ivan.eremin.testchat.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.testchat.presentation.components.phone.data.BorderThickness
import ru.ivan.eremin.testchat.presentation.exception.autofill

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CodeTextField(
    value: String,
    modifier: Modifier = Modifier,
    defaultPaddingValues: PaddingValues = PaddingValues(4.dp, 6.dp),
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    borderThickness: BorderThickness = BorderThickness(),
    autofillType: AutofillType? = AutofillType.PostalCode
){

    OutlinedTextField(
        modifier = modifier
            .autofill(
                autofillType?.let { listOf(it) }.orEmpty(),
                onFill = {
                    onValueChange(it.filter { char -> char.isDigit() }.takeLast(INPUT_LENGTH))
                },
                enabled = autofillType != null && value.isBlank()
            ),
        value = value,
        placeholder = {
            Text(
                text = "Код из смс",
                color = MaterialTheme.colorScheme.primaryContainer
            )
        },
        onValueChange = {
            if (!readOnly && enabled) {
                val newValue = it.filter { char -> char.isDigit() }
                onValueChange(
                    if (newValue.length <= value.length + 1) {
                        newValue.take(INPUT_LENGTH)
                    } else {
                        newValue.takeLast(INPUT_LENGTH)
                    }
                )
            }
        },
        isError = isError,
        supportingText = supportingText
    )
}

private const val INPUT_LENGTH = 10