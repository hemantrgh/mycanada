package com.mycanada.poc.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mycanada.poc.BuildConfig
import com.mycanada.poc.di.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        private val jsonBuilder: Gson = GsonBuilder().setLenient().create()
        private const val TIMEOUT: Long = 2
    }


    @Singleton
    @Provides
    @Named("InformationService")
    fun provideInformationService(): Retrofit {
        val httpClient = OkHttpClient.Builder().connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(TIMEOUT, TimeUnit.MINUTES) // write timeout
                .readTimeout(TIMEOUT, TimeUnit.MINUTES) // read timeout

        return Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(jsonBuilder))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }
}