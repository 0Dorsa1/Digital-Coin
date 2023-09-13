package com.example.digitalcoin.di

import com.example.digitalcoin.network.LivePriceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    internal fun providesLivePriceApi(retrofit: Retrofit): LivePriceApi {
        return retrofit.create(LivePriceApi::class.java)
    }

}