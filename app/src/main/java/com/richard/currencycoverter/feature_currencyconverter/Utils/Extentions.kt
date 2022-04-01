package com.richard.currencycoverter.feature_currencyconverter.Utils

import com.richard.currencycoverter.feature_currencyconverter.data.remote.dto.LiveQuotesDto
import com.richard.currencycoverter.feature_currencyconverter.data.remote.dto.SupportedCurrenciesDto
import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency

fun SupportedCurrenciesDto.toCurrencyList() : List<Currency>{
    val currencyList = mutableListOf<Currency>()
    this.currencies.forEach{
        currencyList.add(
            Currency(
            name = it.value,
            currency_iso = it.key
        )
        )
    }
    return currencyList.toList()
}

fun LiveQuotesDto.toCurrencyList() : List<Currency>{
    val currencyList = mutableListOf<Currency>()
    this.quotes.forEach{
        currencyList.add(
            Currency(
                currency_iso = it.key.substring(3),
                amount = it.value
            )
        )
    }
    return currencyList.toList()
}