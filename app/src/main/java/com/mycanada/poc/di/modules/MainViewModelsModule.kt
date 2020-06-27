package com.mycanada.poc.di.modules

import androidx.lifecycle.ViewModel
import com.mycanada.poc.di.ViewModelKey
import com.mycanada.poc.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}