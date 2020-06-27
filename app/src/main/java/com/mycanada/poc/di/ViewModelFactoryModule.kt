package com.mycanada.poc.di

import androidx.lifecycle.ViewModelProvider
import com.mycanada.poc.viewmodel.MyCanadaAppViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: MyCanadaAppViewModelFactory): ViewModelProvider.Factory
}