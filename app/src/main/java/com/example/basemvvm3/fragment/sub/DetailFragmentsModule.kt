package com.example.basemvvm3.fragment.sub

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailFragmentsModule {
    @ContributesAndroidInjector
    internal abstract fun providePersonDetailFragment(): PersonDetailFragment
}