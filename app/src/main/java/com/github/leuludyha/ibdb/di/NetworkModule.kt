package com.github.leuludyha.ibdb.di

import com.github.leuludyha.data.api.LibraryApi
import com.github.leuludyha.domain.util.NetworkProvider
import com.github.leuludyha.ibdb.BuildConfig
import com.github.leuludyha.ibdb.util.NetworkUtils
import com.google.firebase.ktx.Firebase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Provides all the dependency injection related to the network interface. (Retrofit, WebAPI)
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.OPENLIBRARY_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLibraryApi(retrofit: Retrofit): LibraryApi {
        return retrofit.create(LibraryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(): Firebase {
        return Firebase
    }

    @Provides
    @Singleton
    fun provideNetworkProvider(): NetworkProvider {
        return NetworkUtils
    }
}