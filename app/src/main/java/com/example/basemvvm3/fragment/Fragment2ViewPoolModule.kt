package com.example.basemvvm3.fragment

import androidx.recyclerview.widget.RecyclerView
import com.example.basemvvm3.di.scope.FragmentScoped
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
internal class Fragment2ViewPoolModule {

    @FragmentScoped
    @Provides
    @Named("personListViewPool")
    fun providePersonListViewPool(): RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
}