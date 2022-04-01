package com.richard.currencycoverter.feature_currencyconverter.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["currency_iso"], unique = true)])
data class Currency(
    val name : String = "",
    val currency_iso : String,
    val amount : Double = 0.0,
    @PrimaryKey val id: Int? = null
)
