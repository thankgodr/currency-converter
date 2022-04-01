package com.richard.currencycoverter.feature_currencyconverter.domain.use_case

data class GeneralUseCase(
    val fetchSupportedCurrenciesFromRemote : FetchSupportedCurrencyFromRemote,
    val getSingleCurrencyFromDb : GetSingleCurrencyUseCase,
    val convertLocally : LocalConvertUseCase,
    val convertRemotely : RemoteConvertUseCase,
    val saveCurrencies : SaveCurrencyUseCase,
    val getCurrencisFromDb : GetSuppotedCurrencyUseCase
)
