package com.dsantano.yourconciergeapp.api.generators

import com.dsantano.yourconciergeapp.api.YourConciergeService
import com.dsantano.yourconciergeapp.common.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServiceGenerator {
    @Singleton
    @Provides
    @Named("url")
    fun provideBaseUrl(): String = Constants.API_BASE_URL

    @Singleton
    @Provides
    fun createClient(@Named("url") baseUrl: String): YourConciergeService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).addInterceptor(AuthTokenInterceptor()).build())
            .build()
            .create(YourConciergeService::class.java)
    }
}