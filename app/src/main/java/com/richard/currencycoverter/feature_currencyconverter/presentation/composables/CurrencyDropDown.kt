package com.richard.currencycoverter.feature_currencyconverter.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.richard.currencycoverter.R
import com.richard.currencycoverter.feature_currencyconverter.presentation.ConvertViewModel

@Composable
fun CurrencyDropDown(modifier: Modifier, viewModel: ConvertViewModel = hiltViewModel()){
    var expanded by remember { mutableStateOf(false) }
    val suggestions = viewModel.currenciesState.value.currencoes

    var textfieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown


    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = viewModel.fromCurreny.value.currency_iso,
            onValueChange = { viewModel.updateFromCurrency(it) },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { testTag = "dropdownStart" }
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = {Text(stringResource(id = R.string.selectCurrency))},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
                .semantics { testTag ="mainDropDown" }
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    viewModel.fromCurreny.value = label
                    expanded = false

                }) {
                    Text(text = label.currency_iso)
                }
            }
        }
    }
}