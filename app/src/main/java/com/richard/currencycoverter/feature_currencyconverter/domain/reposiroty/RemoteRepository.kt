package com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty

import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency

interface RemoteRepository {
    suspend fun getSupoortedCurrencies() : List<Currency>
    suspend fun getLiveQuotes() : List<Currency>
    suspend fun remoteConvert(fromIso: String, toIso : String, amount : Double) : Double
}