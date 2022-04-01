package com.richard.currencycoverter.feature_currencyconverter.data.repository

import com.google.gson.Gson
import com.richard.currencycoverter.feature_currencyconverter.Utils.toCurrencyList
import com.richard.currencycoverter.feature_currencyconverter.data.remote.dto.ConversionDto
import com.richard.currencycoverter.feature_currencyconverter.data.remote.dto.LiveQuotesDto
import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImplFaker @Inject constructor(
) :
    RemoteRepository {


    val responseSampleGetQuotes = "{    \"success\": true,    \"terms\": \"https://currencylayer.com/terms\",    \"privacy\": \"https://currencylayer.com/privacy\",    \"timestamp\": 1430401802,    \"source\": \"USD\",    \"quotes\": {        \"USDAED\": 3.672982,        \"USDAFN\": 57.8936,        \"USDALL\": 126.1652,        \"USDAMD\": 475.306,        \"USDANG\": 1.78952,        \"USDAOA\": 109.216875,        \"USDARS\": 8.901966,        \"USDAUD\": 1.269072,        \"USDAWG\": 1.792375,        \"USDAZN\": 1.04945,        \"USDBAM\": 1.757305   }}  "
    val responseConvertSample = "{    \"success\": true,    \"terms\": \"https://currencylayer.com/terms\",    \"privacy\": \"https://currencylayer.com/privacy\",    \"query\": {        \"from\": \"USD\",        \"to\": \"GBP\",        \"amount\": 10    },    \"info\": {        \"timestamp\": 1104623999,        \"quote\": 0.51961    },    \"historical\": true,    \"date\": \"2005-01-01\",    \"result\": 5.1961}"

    val gson = Gson()
    override suspend fun getSupoortedCurrencies(): List<Currency> {
        return gson.fromJson(responseSampleGetQuotes, LiveQuotesDto::class.java).toCurrencyList()
    }

    override suspend fun getLiveQuotes(): List<Currency> {
        return gson.fromJson(responseSampleGetQuotes, LiveQuotesDto::class.java).toCurrencyList()
    }

    override suspend fun remoteConvert(fromIso: String, toIso: String, amount: Double): Double {
        return gson.fromJson(responseConvertSample, ConversionDto::class.java).result
    }
}