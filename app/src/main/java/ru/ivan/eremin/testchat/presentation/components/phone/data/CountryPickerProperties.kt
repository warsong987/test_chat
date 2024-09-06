package ru.ivan.eremin.testchat.presentation.components.phone.data

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CountryPickerProperties(
    val showCountryFlag: Boolean = true,
    val showCountryPhoneCode: Boolean = true,
    val showCountryName: Boolean = false,
    val showCountryCode: Boolean = false,
    val spaceAfterCountryFlag: Dp = 8.dp,
    val spaceAfterCountryPhoneCode: Dp = 6.dp,
    val spaceAfterCountryName: Dp = 6.dp,
    val spaceAfterCountryCode: Dp = 6.dp,
)