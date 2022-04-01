package com.richard.currencycoverter.feature_currencyconverter.presentation

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.richard.currencycoverter.core.Resource
import com.richard.currencycoverter.feature_currencyconverter.domain.background_workers.FetchQuotesWorker
import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import com.richard.currencycoverter.feature_currencyconverter.domain.use_case.GeneralUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class ConvertViewModel @Inject constructor(
    private val generalUseCase: GeneralUseCase,
    @ApplicationContext private val application : Context
) : ViewModel() {
    private var getCurrencyJob : Job? = null
    private val _currenciesState = mutableStateOf(CurrenciesStateWrapper())
    val currenciesState : State<CurrenciesStateWrapper> = _currenciesState

    //Offline Convertion
    private val isOffline = true

    //Loading status
    private val _isloading = mutableStateOf(true)
    val isLoading : State<Boolean> = _isloading

    //Conversion params
    val fromCurreny = mutableStateOf(Currency(currency_iso = "USD", amount = 1.0 ))
    val toCurrency = mutableStateOf(Currency(currency_iso = "USD", amount = 1.0  ))
    private val _amount = mutableStateOf(0)
    val amount : State<Int> = _amount


    //Convertion results
    private val _result = mutableStateOf(0.0)
    val result : State<Double> = _result

    init {
        getCurrencies()
    }

    //Get currencies from database or remotes
    private fun getCurrencies(){
       getCurrencyJob?.cancel()
        getCurrencyJob = generalUseCase
            .getCurrencisFromDb()
            .onEach {
                _currenciesState.value = CurrenciesStateWrapper(it)
                _isloading.value = false
                if(it.isEmpty()){
                    fetchCurrencyFromRemote()
                }
            }
            .launchIn(viewModelScope)

        startWookers()

    }

    //Get Currency from remote
    private fun fetchCurrencyFromRemote(){
        generalUseCase.fetchSupportedCurrenciesFromRemote()
            .onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _isloading.value = true
                        _currenciesState.value = CurrenciesStateWrapper(isLoading = true)
                    }
                    is Resource.Error -> {
                        _isloading.value = false
                        result.message?.let {
                            _currenciesState.value = CurrenciesStateWrapper(error = true, message = it)
                        }
                    }
                    is Resource.Success -> {
                        _isloading.value = false
                        result.data?.let {
                            _currenciesState.value = CurrenciesStateWrapper(it)
                            saveCurrenciesToDb(it)
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    //Save Currencies to room database
    private fun saveCurrenciesToDb(currecies : List<Currency>){
       viewModelScope.launch {
           generalUseCase.saveCurrencies(*currecies.toTypedArray())
       }
    }

    //Convert
     fun convert(){
       generalUseCase.convertRemotely(
           fromCurreny.value.currency_iso,
           toCurrency.value.currency_iso,
           amount.value.toDouble()
       ).onEach { result ->
           when(result){
               is Resource.Loading -> {
                   _isloading.value = true
               }
               is Resource.Error -> {
                    _isloading.value = false
                   localConvert()
                   roundOff()
               }
               is Resource.Success -> {
                   _isloading.value = false
                   result.data?.let {
                       if(it == 0.0 && _amount.value == 0){
                           _result.value = 0.0
                       }
                       else if(it == 0.0 && _amount.value > 0){
                           localConvert()
                       }else{
                           _result.value = it
                       }
                   }
               }
           }
       }.launchIn(viewModelScope)
    }

    //Events
    fun updateAmount(newAmount : Int){
        _amount.value = newAmount
    }

    fun updateCurrencyGrid(selected : Currency = fromCurreny.value) : State<List<Currency>>{
        val currencies = _currenciesState.value.currencoes.filter {
            selected != it
        }
        return mutableStateOf(currencies)
    }

    fun updateFromCurrency(curr: String) {
        fromCurreny.value = currenciesState.value.currencoes.find {
            it.currency_iso == curr
        }!!
    }


    private fun roundOff(){
      _result.value =   (_result.value * 1000.0).roundToInt() / 1000.0
    }

    //Start worker to get quotes every 30 minutes
    internal fun startWookers(){
        val getQuotesRequest = PeriodicWorkRequestBuilder<FetchQuotesWorker>(30, TimeUnit.MINUTES)
            .setInitialDelay(30, TimeUnit.MINUTES)
            .build()
        val workManager = WorkManager.getInstance(application)
        workManager.enqueue(getQuotesRequest)
    }

    private fun localConvert(){
        _result.value = generalUseCase.convertLocally(
            fromCurreny.value,
            toCurrency.value,
            amount.value.toDouble())
        roundOff()
    }
}