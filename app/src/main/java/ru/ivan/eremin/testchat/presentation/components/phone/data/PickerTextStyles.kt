package ru.ivan.eremin.testchat.presentation.components.phone.data

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

data class PickerTextStyles(
    val countryPhoneCodeTextStyle: TextStyle = TextStyle(fontWeight = FontWeight.Bold),
    val countryNameTextStyle: TextStyle = TextStyle(),
    val countryCodeTextStyle: TextStyle = TextStyle()
)