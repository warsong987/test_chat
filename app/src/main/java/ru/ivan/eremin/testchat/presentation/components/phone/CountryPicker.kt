package ru.ivan.eremin.testchat.presentation.components.phone

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.testchat.presentation.components.phone.data.CountryDetails
import ru.ivan.eremin.testchat.presentation.components.phone.data.CountryPickerProperties
import ru.ivan.eremin.testchat.presentation.components.phone.data.Dimensions
import ru.ivan.eremin.testchat.presentation.components.phone.utils.FunctionHelper

@Composable
fun CountryPicker(
    modifier: Modifier = Modifier,
    defaultPaddingValues: PaddingValues = PaddingValues(4.dp, 6.dp),
    countryPickerProperties: CountryPickerProperties = CountryPickerProperties(),
    countryFlagDimensions: Dimensions = Dimensions(),
    defaultCountryCode: String? = null,
    country: CountryDetails? = null,
    countriesList: List<String>? = null,
    onCountrySelected: (country: CountryDetails) -> Unit
) {

    val context = LocalContext.current
    var openCountrySelectionDialog by remember { mutableStateOf(false) }
    val countryList = remember {
        if (countriesList.isNullOrEmpty()) {
            FunctionHelper.getAllCountries(context)
        } else {
            val updatedCountriesList = countriesList.map { it.lowercase() }
            FunctionHelper.getAllCountries(context)
                .filter { updatedCountriesList.contains(it.countryCode) }
        }
    } 
    var selectedCountry by remember(country) {
        val selectedCountry =
            country ?: FunctionHelper.getDefaultSelectedCountry(
                context,
                defaultCountryCode?.lowercase(),
                countryList
            )
        onCountrySelected(selectedCountry)
        mutableStateOf(selectedCountry)
    }
    if (openCountrySelectionDialog) {
        CountrySelectionDialog(
            countryList = countryList,
            onDismissRequest = {
                openCountrySelectionDialog = false
            },
            onSelected = {
                selectedCountry = it
                openCountrySelectionDialog = false
                onCountrySelected(it)
            },
        )
    }
    SelectedCountrySection(
        defaultPaddingValues = defaultPaddingValues,
        selectedCountry = selectedCountry,
        countryPickerProperties = countryPickerProperties,
        countryFlagDimensions = countryFlagDimensions,
        modifier = modifier
    ) {
        openCountrySelectionDialog = !openCountrySelectionDialog
    }
}

@Composable
private fun SelectedCountrySection(
    defaultPaddingValues: PaddingValues,
    selectedCountry: CountryDetails,
    countryPickerProperties: CountryPickerProperties,
    countryFlagDimensions: Dimensions,
    modifier: Modifier = Modifier,
    onSelectCountry: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable {
                onSelectCountry()
            }
            .padding(defaultPaddingValues),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (countryPickerProperties.showCountryFlag) {
            Image(
                modifier = Modifier
                    .width(countryFlagDimensions.width)
                    .height(countryFlagDimensions.height),
                painter = painterResource(selectedCountry.countryFlag),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(countryPickerProperties.spaceAfterCountryFlag))
        }
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
    }
}