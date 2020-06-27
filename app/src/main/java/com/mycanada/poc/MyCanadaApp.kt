package com.mycanada.poc

import android.app.Application
import com.mycanada.poc.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyCanadaApp: DaggerApplication() {

    init {
        instance = this
    }

    companion object {

        private var instance: MyCanadaApp? = null

        fun getInstance(): MyCanadaApp {
            return instance as MyCanadaApp
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?>? {
        return DaggerAppComponent.builder().application(this)!!.build()
    }

    override fun onCreate() {
        super.onCreate()
//        DaggerAppComponent.builder().application(this)!!.build()?.inject(this)
    }

}