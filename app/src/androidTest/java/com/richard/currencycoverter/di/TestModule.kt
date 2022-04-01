package com.richard.currencycoverter.di

import com.richard.currencycoverter.feature_currencyconverter.data.repository.CoverterRepositoryImplFaker
import com.richard.currencycoverter.feature_currencyconverter.data.repository.RemoteRepositoryImplFaker
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.ConverterRepository
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.RemoteRepository
import com.richard.currencycoverter.feature_currencyconverter.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {
    @Provides
    @Singleton
    fun provideTestRemoteImp() : RemoteRepository{
        return RemoteRepositoryImplFaker()
    }

    @Provides
    @Singleton
    fun provideTestConverterImp() : ConverterRepository{
        return CoverterRepositoryImplFaker()
    }



    @Provides
    @Singleton
    fun provideGeneralUseCase(
        remoteRepository: RemoteRepository,
        converterRepository: ConverterRepository
    ) : GeneralUseCase{
        return GeneralUseCase(
            FetchSupportedCurrencyFromRemote(remoteRepository),
            GetSingleCurrencyUseCase(converterRepository),
            LocalConvertUseCase(),
            RemoteConvertUseCase(remoteRepository),
            SaveCurrencyUseCase(converterRepository),
            GetSuppotedCurrencyUseCase(converterRepository)
        )
    }
}
