package com.example.basemvvm3.di.module

import com.example.basemvvm3.activities.MainActivity
import com.example.basemvvm3.activities.MainActivityModule
import com.example.basemvvm3.di.scope.ActivityScoped
import com.example.basemvvm3.fragment.MainFragment2Module
import com.example.basemvvm3.fragment.MainFragmentModule
import com.example.basemvvm3.fragment.dialog.DialogModule
import com.example.basemvvm3.fragment.sub.DetailFragmentsModule
import com.example.basemvvm3.fragment.sub.SubFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            MainFragmentModule::class,
            MainFragment2Module::class,
            SubFragmentModule::class,
            DetailFragmentsModule::class,
            DialogModule::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity
}