package com.mycanada.poc.di

import android.app.Application

import com.mycanada.poc.MyCanadaApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [  AndroidSupportInjectionModule::class,
                        MainActivityModule::class,
                        AppModule::class,
                        ViewModelFactoryModule::class
                     ])
interface AppComponent : AndroidInjector<MyCanadaApp?> {
   @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): AppComponent?
    }

}