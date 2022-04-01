package com.richard.currencycoverter.core

import com.richard.currencycoverter.BuildConfig

object Constants {
    const val BASE_URL = "http://api.currencylayer.com/"
    const val access_token = BuildConfig.API_KEY
    const val fetchQuotesKey = "fetchquotes"
}