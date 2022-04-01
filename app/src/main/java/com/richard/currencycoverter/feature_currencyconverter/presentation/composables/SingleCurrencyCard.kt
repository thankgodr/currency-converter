package com.richard.currencycoverter.feature_currencyconverter.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import com.richard.currencycoverter.feature_currencyconverter.presentation.ConvertViewModel

@Composable
fun SingleCurrencyCard(currency: Currency, viewModel: ConvertViewModel = hiltViewModel()){
    Card(
        elevation = 5.dp,
        modifier = Modifier.padding(3.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = {
                viewModel.toCurrency.value = currency
                viewModel.convert()
            })
            .semantics { testTag = "singleCurrency" }
        ){
            Text(
                text = currency.currency_iso,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}