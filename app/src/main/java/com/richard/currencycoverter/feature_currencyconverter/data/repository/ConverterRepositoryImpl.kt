package com.richard.currencycoverter.feature_currencyconverter.data.repository

import com.richard.currencycoverter.feature_currencyconverter.data.data_source.CurrencyDao
import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.ConverterRepository
import kotlinx.coroutines.flow.Flow

class ConverterRepositoryImpl(
    private val dao : CurrencyDao
) : ConverterRepository {
    override fun getCurrencies(): Flow<List<Currency>> {
       return dao.getCurrencies()
    }

    override suspend fun insertCurrency(vararg currency: Currency) {
         dao.insertCurrency(*currency)
    }

    override suspend fun getCurrencyByIso(iso: String): Currency? {
       return dao.getCurrenccyByIso(iso)
    }
}