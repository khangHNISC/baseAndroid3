package com.example.basemvvm3.fragment

import androidx.lifecycle.ViewModel
import com.example.basemvvm3.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class MainFragmentModule  {

    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): MainFragment

    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainFragmentViewModel): ViewModel
}