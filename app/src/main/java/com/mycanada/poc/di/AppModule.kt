package com.mycanada.poc.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mycanada.poc.api.InformationAPI
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
        private const val BASE_URL = "https://dl.dropboxusercontent.com/"
        private val jsonBuilder: Gson = GsonBuilder().setLenient().create()
    }


    @Singleton
    @Provides
    @Named("InformationService")
    fun provideInformationService(): Retrofit {
        val httpClient = OkHttpClient.Builder().connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES) // write timeout
                .readTimeout(2, TimeUnit.MINUTES) // read timeout*/

        return Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(jsonBuilder))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }
}