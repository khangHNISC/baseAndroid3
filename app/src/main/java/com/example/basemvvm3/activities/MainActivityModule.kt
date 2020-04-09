package com.example.basemvvm3.activities

import androidx.lifecycle.ViewModel
import com.example.basemvvm3.di.ViewModelKey
import com.example.basemvvm3.fragment.FragmentInfo
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    internal abstract fun bindFragmentInfo(): FragmentInfo
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainActivityViewModel): ViewModel
}