package com.example.basemvvm3.di.module

import com.example.basemvvm3.activities.MainActivity
import com.example.basemvvm3.activities.MainActivityModule
import com.example.basemvvm3.di.scope.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity
}