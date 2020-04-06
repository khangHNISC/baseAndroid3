package com.example.basemvvm3.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm3.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory):
            ViewModelProvider.Factory
}