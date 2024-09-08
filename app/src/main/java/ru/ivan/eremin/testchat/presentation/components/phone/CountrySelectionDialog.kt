package ru.ivan.eremin.testchat.presentation.components.phone

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import ru.ivan.eremin.testchat.R
import ru.ivan.eremin.testchat.presentation.components.Text
import ru.ivan.eremin.testchat.presentation.components.phone.data.CountryDetails
import ru.ivan.eremin.testchat.presentation.components.phone.utils.FunctionHelper.searchForCountry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySelectionDialog(
    modifier: Modifier = Modifier,
    countryList: List<CountryDetails>,
    onDismissRequest: () -> Unit,
    onSelected: (item: CountryDetails) -> Unit,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false)
) {
    var searchValue by remember { mutableStateOf("") }
    var isSearchEnabled by remember { mutableStateOf(false) }
    var filteredCountries by remember { mutableStateOf(countryList) }
    BasicAlertDialog(
        modifier = Modifier
            .fillMaxSize(),
        onDismissRequest = onDismissRequest,
        properties = properties,
        content = {
            Surface(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Scaffold(
                    topBar = {
                        val focusRequester = remember { FocusRequester() }
                        LaunchedEffect(isSearchEnabled) {
                            if (isSearchEnabled) {
                                focusRequester.requestFocus()
                            }
                        }
                        CenterAlignedTopAppBar(
                            title = {
                                if (isSearchEnabled) {
                                    TextField(
                                        modifier = Modifier.focusRequester(focusRequester),
                                        value = searchValue,
                                        onValueChange = { searchStr ->
                                            searchValue = searchStr
                                            filteredCountries =
                                                countryList.searchForCountry(searchStr)
                                        },
                                        placeholder = {
                                            Text(
                                                text = stringResource(R.string.search_country),
                                                color = MaterialTheme.colorScheme.onSurface,
                                            )
                                        },
                                        colors = TextFieldDefaults.colors(
                                            disabledContainerColor = Color.Transparent,
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                        ),
                                        textStyle = MaterialTheme.typography.labelLarge,
                                    )
                                } else {
                                    Text(
                                        modifier = Modifier.offset(y = (-2).dp),
                                        text = stringResource(R.string.select_country),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    onDismissRequest()
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = null,
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = {
                                    isSearchEnabled = !isSearchEnabled
                                }) {
                                    Icon(
                                        imageVector = if (isSearchEnabled) Icons.Default.Clear else Icons.Default.Search,
                                        contentDescription = null,
                                    )
                                }
                            },
                        )
                    },
                ) { paddingValues ->
                    LazyColumn(
                        Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                    ) {
                        val countriesData =
                            if (searchValue.isEmpty()) {
                                countryList
                            } else {
                                filteredCountries
                            }
                        items(countriesData) { countryItem ->
                            ListItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSelected(countryItem)
                                    },
                                leadingContent = {
                                    Image(
                                        modifier = modifier.width(30.dp),
                                        painter = painterResource(id = countryItem.countryFlag),
                                        contentDescription = null,
                                    )
                                },
                                trailingContent = {
                                    Text(
                                        text = countryItem.countryPhoneNumberCode,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                },
                                headlineContent = {
                                    Text(
                                        text = countryItem.countryName,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                },
                            )
                        }
                    }
                }
            }
        },
    )
}