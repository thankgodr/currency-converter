package com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty

import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface ConverterRepository {
    fun getCurrencies() : Flow<List<Currency>>
    suspend fun insertCurrency(vararg currency: Currency)
    suspend fun getCurrencyByIso(iso: String) : Currency?
}