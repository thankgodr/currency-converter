package com.richard.currencycoverter.di

import android.app.Application
import androidx.room.Room
import com.richard.currencycoverter.core.Constants
import com.richard.currencycoverter.feature_currencyconverter.data.data_source.CurrencyDatabase
import com.richard.currencycoverter.feature_currencyconverter.data.remote.CurrencyLayerApi
import com.richard.currencycoverter.feature_currencyconverter.data.repository.ConverterRepositoryImpl
import com.richard.currencycoverter.feature_currencyconverter.data.repository.RemoteRepositoryImpl
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.ConverterRepository
import com.richard.currencycoverter.feature_currencyconverter.domain.reposiroty.RemoteRepository
import com.richard.currencycoverter.feature_currencyconverter.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCurrencyLayerApi(): CurrencyLayerApi{
        val client = OkHttpClient.Builder()
            .addInterceptor{
                    chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("access_key", Constants.access_token)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }.build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyLayerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(api : CurrencyLayerApi): RemoteRepository {
        return RemoteRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCurrencyDatabase(app : Application) : CurrencyDatabase{
        return Room.databaseBuilder(
            app,
            CurrencyDatabase::class.java,
            CurrencyDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideConverterRepository(db : CurrencyDatabase) : ConverterRepository{
        return ConverterRepositoryImpl(db.currencyDao)
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
