package com.example.digitalcoin.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {
    @Provides
    @Singleton
    internal fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://185.159.153.156/")
            .client(NetworkClient().build())
            .build()
    }
}

class NetworkClient {
    fun build(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(logInterceptor())
        return builder.build()
    }
}

private fun logInterceptor(): Interceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}