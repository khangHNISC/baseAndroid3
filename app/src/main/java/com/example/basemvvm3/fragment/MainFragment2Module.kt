package com.example.basemvvm3.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MainFragment2Module  {

    @ContributesAndroidInjector
    internal abstract fun contributeFragment2(): MainFragment2
}