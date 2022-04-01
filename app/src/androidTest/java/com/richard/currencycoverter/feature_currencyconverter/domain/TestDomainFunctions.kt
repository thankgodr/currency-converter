package com.richard.currencycoverter.feature_currencyconverter.domain

import android.util.Log
import com.richard.currencycoverter.core.Resource
import com.richard.currencycoverter.di.AppModule
import com.richard.currencycoverter.feature_currencyconverter.domain.use_case.GeneralUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(AppModule::class)
@HiltAndroidTest
class TestDomainFunctions {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var useCases : GeneralUseCase

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Test
    fun testFetchremoteSuscess() = runBlocking {
      val quotess =  useCases.fetchSupportedCurrenciesFromRemote()
        val result = quotess.last()
        assert(result is Resource.Success)
    }

    @Test
    fun testSaveCurrencies() = runBlocking {
        val quotess =  useCases.fetchSupportedCurrenciesFromRemote()
        val result = quotess.last()
        val saveCurrencyUseCase = useCases.saveCurrencies(*result.data!!.toTypedArray())
        val dte = useCases.getCurrencisFromDb().last()
        assert(!dte.isEmpty())
    }

    @Test
    fun testRemoteConvertion() = runBlocking {
        val result = useCases.convertRemotely("TET","TST", 3.0).last()
        assert(result.data!! > 0)
    }

    @Test
    fun testLocalConvertion() = runBlocking {
        val quotess =  useCases.fetchSupportedCurrenciesFromRemote()
        val response = quotess.last()
        val saveCurrencyUseCase = useCases.saveCurrencies(*response.data!!.toTypedArray())
        var result = useCases.convertLocally(response.data!![1],response.data!![0], 20.0)
        Log.i("okh", result.toString())
        assert(result.equals(1.2688732433291419))
    }

}