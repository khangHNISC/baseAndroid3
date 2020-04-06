package com.example.basemvvm3.activities

import androidx.lifecycle.ViewModel
import com.example.basemvvm3.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainActivityViewModel): ViewModel
}