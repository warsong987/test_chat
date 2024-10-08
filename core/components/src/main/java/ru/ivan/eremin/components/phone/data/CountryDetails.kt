package ru.ivan.eremin.components.phone.data

import androidx.annotation.DrawableRes

data class CountryDetails(
    var countryCode: String,
    val countryPhoneNumberCode: String,
    val countryName: String,
    @DrawableRes val countryFlag: Int,
    val mask: String
)