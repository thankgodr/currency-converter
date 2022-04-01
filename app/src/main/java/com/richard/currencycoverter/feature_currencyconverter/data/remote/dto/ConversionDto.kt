package com.richard.currencycoverter.feature_currencyconverter.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ConversionDto(
    @SerializedName("info")
    val info: Info,
    @SerializedName("privacy")
    val privacy: String,
    @SerializedName("query")
    val query: Query,
    @SerializedName("result")
    val result: Double,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("terms")
    val terms: String
)