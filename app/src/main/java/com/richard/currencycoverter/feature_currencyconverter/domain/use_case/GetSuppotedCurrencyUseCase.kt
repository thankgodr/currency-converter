package com.richard.currencycoverter.feature_currencyconverter.domain.use_case

import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.ConverterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSuppotedCurrencyUseCase @Inject constructor(
    private val converterRepository: ConverterRepository
) {
    operator fun invoke() : Flow<List<Currency>> {
        return converterRepository.getCurrencies()
    }
}