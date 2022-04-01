package com.richard.currencycoverter.feature_currencyconverter.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("quote")
    val quote: Double,
    @SerializedName("timestamp")
    val timestamp: Int
)