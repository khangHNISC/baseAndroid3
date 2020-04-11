package com.example.basemvvm3.fragment.sub

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class SubFragmentModule  {

    @ContributesAndroidInjector
    internal abstract fun contributeSubFragment(): SubFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSubFragment2(): SubFragment2
}