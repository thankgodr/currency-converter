package com.richard.currencycoverter.feature_currencyconverter.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LiveQuotesDto(
    @SerializedName("privacy")
    val privacy: String,
    @SerializedName("quotes")
    val quotes: Map<String, Double>,
    @SerializedName("source")
    val source: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("terms")
    val terms: String,
    @SerializedName("timestamp")
    val timestamp: Int
)