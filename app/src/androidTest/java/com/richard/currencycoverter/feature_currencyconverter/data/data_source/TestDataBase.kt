package com.richard.currencycoverter.feature_currencyconverter.data.data_source

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TestDataBase {
    private lateinit var currencyDao: CurrencyDao
    private lateinit var db : CurrencyDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CurrencyDatabase::class.java).build()
        currencyDao = db.currencyDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun currencyCanBeAddedToDatabase(){
        var result : Currency? = null
        val newCurrency = Currency("Test", "TST", 3.948)
        runBlocking {
            currencyDao.insertCurrency(newCurrency)
            result = currencyDao.getCurrenccyByIso(newCurrency.currency_iso)
        }
        assert(result != null)
    }

    @Test
    @Throws(Exception::class)
    fun canAddMultipleCurrenciesAtOnce(){
        var result = emptyList<Currency>()
        val newCurrency = Currency("Test", "TST", 3.948)
        val newCurrency2 = Currency("Test2", "TST2", 3.548)

        runBlocking {
            currencyDao.insertCurrency(newCurrency, newCurrency2)

           result = currencyDao.getCurrencies().first()
        }
        assert(result.size > 0)
    }

    @Test
    @Throws(Exception::class)
    fun canSearchDatabaseForSpecificCurencies(){
        var result : Currency?
        val newCurrency = Currency("Test", "TST", 3.948)
        val newCurrency0 = Currency("CAD Dollar", "CAD", 3.548)
        val newCurrency3 = Currency("US Dollar", "USD", 1.0)
        val newCurrency2 = Currency("Test2", "TST2", 3.548)
        runBlocking {
            currencyDao.insertCurrency(newCurrency,newCurrency0,newCurrency2,newCurrency3)
          result =  currencyDao.getCurrenccyByIso("USD")
        }
        assert(result?.currency_iso == "USD")

    }
}