package com.richard.currencycoverter.feature_currencyconverter.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency

@Database(
    entities = [Currency::class],
    version = 1
)
abstract class CurrencyDatabase : RoomDatabase()  {
    abstract val currencyDao : CurrencyDao

    companion object {
        const val DATABASE_NAME = "currency_converter_db"
    }
}