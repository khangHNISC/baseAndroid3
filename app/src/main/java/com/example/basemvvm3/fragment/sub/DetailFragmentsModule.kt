package com.example.basemvvm3.fragment.sub

import com.example.basemvvm3.di.scope.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailFragmentsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun providePersonDetailFragment(): PersonDetailFragment
}