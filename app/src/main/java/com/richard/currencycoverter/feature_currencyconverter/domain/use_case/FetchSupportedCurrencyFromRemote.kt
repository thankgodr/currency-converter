package com.richard.currencycoverter.feature_currencyconverter.domain.use_case

import com.richard.currencycoverter.core.Resource
import com.richard.currencycoverter.feature_currencyconverter.domain.model.Currency
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FetchSupportedCurrencyFromRemote @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    operator fun invoke() : Flow<Resource<List<Currency>>> = flow {
        try{
            emit(Resource.Loading<List<Currency>>())
            val res = remoteRepository.getLiveQuotes()
            emit(Resource.Success<List<Currency>>(res))
        }catch (e : HttpException){
            emit(Resource.Error<List<Currency>>(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e : IOException){
            emit(Resource.Error<List<Currency>>("Couldn't reach server. Check your internet connection."))
        }catch (e : Exception){
            emit(Resource.Error<List<Currency>>(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}