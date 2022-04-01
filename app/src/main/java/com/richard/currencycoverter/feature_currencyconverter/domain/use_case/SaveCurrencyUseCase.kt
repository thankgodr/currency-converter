package com.richard.currencycoverter.feature_currencyconverter.domain.use_case

import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.ConverterRepository
import javax.inject.Inject

class SaveCurrencyUseCase @Inject constructor(
    private val converterRepository: ConverterRepository
) {
    @Throws(Exception::class)
    suspend operator fun invoke(vararg currency: Currency){
        try{
            converterRepository.insertCurrency(*currency)
        }catch (e : Exception){
            throw Exception(e.message)
        }
    }
}