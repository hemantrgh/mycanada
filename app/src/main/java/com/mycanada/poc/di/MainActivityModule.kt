package com.mycanada.poc.di

import com.mycanada.poc.di.modules.InformationModule
import com.mycanada.poc.di.modules.InformationScope
import com.mycanada.poc.di.modules.MainViewModelsModule
import com.mycanada.poc.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @InformationScope
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class,
                                           InformationModule::class,
                                           MainViewModelsModule::class])
    abstract fun contributeMainActivity(): MainActivity

}