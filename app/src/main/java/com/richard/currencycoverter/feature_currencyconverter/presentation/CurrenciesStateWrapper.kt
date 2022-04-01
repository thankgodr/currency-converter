package com.richard.currencycoverter.feature_currencyconverter.presentation

import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency


data class CurrenciesStateWrapper(
    val currencoes : List<Currency> = emptyList(),
    val isLoading  : Boolean = false,
    val error : Boolean = false,
    val message : String = ""
)
