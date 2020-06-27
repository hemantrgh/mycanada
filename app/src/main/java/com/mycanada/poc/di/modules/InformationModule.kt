package com.mycanada.poc.di.modules

import com.mycanada.poc.AppExecutors
import com.mycanada.poc.api.InformationAPI
import com.mycanada.poc.repository.InformationRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class InformationModule {

    @InformationScope
    @Provides
    fun providesInformationRepository(appExecutors: AppExecutors, @Named("InformationService") informationService: Retrofit): InformationRepository {
        return InformationRepository(appExecutors, informationService.create(InformationAPI::class.java))
    }

}