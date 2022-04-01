package com.richard.currencycoverter.feature_currencyconverter.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.richard.currencycoverter.feature_currencyconverter.presentation.composables.CurrencyDropDown
import com.richard.currencycoverter.feature_currencyconverter.presentation.composables.GridCurrency

@Composable
fun ConverterScreen(viewModel: ConvertViewModel = hiltViewModel()){
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }

    Box(modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .semantics { testTag = "amount" }
                    .fillMaxWidth(),
                value = viewModel.amount.value.toString(),
                onValueChange = {
                    viewModel.updateAmount(it.toInt())
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "${viewModel.result.value.toString()} ${viewModel.toCurrency.value.currency_iso}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(CenterVertically)
                        .weight(2f)
                        .semantics { testTag = "result" }
                )
                CurrencyDropDown(modifier = Modifier.weight(1f))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                GridCurrency()
            }
        }

        if(viewModel.isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}