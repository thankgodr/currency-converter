package com.richard.currencycoverter.feature_currencyconverter.domain.use_case

import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import javax.inject.Inject

class LocalConvertUseCase @Inject constructor(){
  operator fun invoke(from: Currency, to : Currency, amount: Double) : Double {
    if(from.currency_iso == "USD"){
      return amount * to.amount
    }
    else if(to.currency_iso == "USD"){
      return amount / from.amount
    }else{
      val dollarValue = amount / from.amount
      return dollarValue * to.amount
    }
  }

}