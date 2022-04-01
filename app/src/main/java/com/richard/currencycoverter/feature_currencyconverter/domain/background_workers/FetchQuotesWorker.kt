package com.richard.currencycoverter.feature_currencyconverter.domain.background_workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.richard.currencycoverter.core.Resource
import com.richard.currencycoverter.feature_currencyconverter.domain.use_case.GeneralUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.last

@HiltWorker
class FetchQuotesWorker @AssistedInject constructor(
    @Assisted appContext : Context,
    @Assisted params : WorkerParameters,
    val usecase: GeneralUseCase,
) : CoroutineWorker(appContext, params) {


    override suspend fun doWork(): Result {

       val info = usecase.fetchSupportedCurrenciesFromRemote().last();
        when(info){
            is Resource.Error -> {
                return Result.failure()
            }
            else -> {
                info.data?.let {
                    usecase.saveCurrencies(*it?.toTypedArray())
                }
                return Result.success()
            }
        }
    }

}