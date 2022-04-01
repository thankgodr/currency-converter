package com.richard.currencycoverter.feature_currencyconverter.data.repository

import com.richard.currencycoverter.feature_currencyconverter.Utils.toCurrencyList
import com.richard.currencycoverter.feature_currencyconverter.data.remote.CurrencyLayerApi
import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    val api : CurrencyLayerApi
) : RemoteRepository {
    override suspend fun getSupoortedCurrencies(): List<Currency> {
        return api.getSupportedCurrencies().toCurrencyList()
    }

    override suspend fun getLiveQuotes(): List<Currency> {
        return api.getLiveQuotes().toCurrencyList()
    }

    override suspend fun remoteConvert(fromIso: String, toIso: String, amount: Double): Double {
        return api.convert(fromIso, toIso, amount).result
    }
}