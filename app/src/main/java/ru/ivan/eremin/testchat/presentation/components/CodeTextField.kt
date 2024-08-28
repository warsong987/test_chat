package ru.ivan.eremin.testchat.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import ru.ivan.eremin.testchat.presentation.exception.autofill

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CodeTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorText: String? = null,
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
        supportingText = {
            errorText?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

private const val INPUT_LENGTH = 10