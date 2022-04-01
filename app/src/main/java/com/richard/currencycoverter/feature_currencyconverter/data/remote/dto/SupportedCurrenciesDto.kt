package com.richard.currencycoverter.feature_currencyconverter.data.remote.dto


import com.google.gson.annotations.SerializedName

data class SupportedCurrenciesDto(
    @SerializedName("currencies")
    val currencies: Map<String, String>,
    @SerializedName("privacy")
    val privacy: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("terms")
    val terms: String
)