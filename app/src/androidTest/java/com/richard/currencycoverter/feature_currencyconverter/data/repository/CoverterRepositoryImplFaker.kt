package com.richard.currencycoverter.feature_currencyconverter.data.repository

import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.ConverterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CoverterRepositoryImplFaker : ConverterRepository {

    val dblist = mutableListOf<Currency>()

    override fun getCurrencies(): Flow<List<Currency>> {
        return flowOf(dblist)
    }

    override suspend fun insertCurrency(vararg currency: Currency) {
        dblist.addAll(currency)
    }

    override suspend fun getCurrencyByIso(iso: String): Currency? {
       return dblist.find {
           it.currency_iso == iso
       }
    }
}