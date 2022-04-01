package com.richard.currencycoverter.feature_currencyconverter.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency")
    fun getCurrencies(): Flow<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(vararg currency: Currency)

    @Query("SELECT * FROM currency WHERE currency_iso = :iso")
    suspend fun getCurrenccyByIso(iso : String) : Currency?

}