package com.example.basemvvm3.fragment

import com.example.basemvvm3.di.scope.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MainFragment2Module {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment2(): MainFragment2
}