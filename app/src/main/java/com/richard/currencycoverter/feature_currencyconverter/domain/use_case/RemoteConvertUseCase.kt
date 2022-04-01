package com.richard.currencycoverter.feature_currencyconverter.domain.use_case

import com.richard.currencycoverter.core.Resource
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class RemoteConvertUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    operator fun invoke(fromIso : String, toIso : String, amount: Double)
    : Flow<Resource<Double>> = flow{
            try{
                emit(Resource.Loading<Double>())
                val result = remoteRepository.remoteConvert(fromIso, toIso, amount)
                emit(Resource.Success<Double>(result))
             }
            catch (e : HttpException){
                emit(Resource.Error<Double>(e.localizedMessage ?: "An unexpected error occured"))
            }catch (e : IOException){
                emit(Resource.Error<Double>("Couldn't reach server. Check your internet connection."))
            }catch (e : Exception){
                emit(Resource.Error<Double>(e.localizedMessage ?: "An unexpected error occured"))
            }
        }
}