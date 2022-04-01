package com.richard.currencycoverter.feature_currencyconverter.domain.use_case

import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.ConverterRepository
import javax.inject.Inject

class GetSingleCurrencyUseCase @Inject constructor(
    private val converterRepository: ConverterRepository
) {
    suspend operator fun invoke(iso : String) : Currency?{
        return converterRepository.getCurrencyByIso(iso)
    }
}