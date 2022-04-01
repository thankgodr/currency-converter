package com.richard.currencycoverter.feature_currencyconverter.data.remote

import com.richard.currencycoverter.feature_currencyconverter.data.remote.dto.ConversionDto
import com.richard.currencycoverter.feature_currencyconverter.data.remote.dto.LiveQuotesDto
import com.richard.currencycoverter.feature_currencyconverter.data.remote.dto.SupportedCurrenciesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyLayerApi {
    @GET("list")
    suspend fun getSupportedCurrencies() : SupportedCurrenciesDto

    @GET("live")
    suspend fun getLiveQuotes() : LiveQuotesDto

    @GET("convert")
    suspend fun convert(
        @Query("from") fromIso : String,
        @Query("to") toIso : String,
        @Query("amount") amount: Double
    ) : ConversionDto

}

