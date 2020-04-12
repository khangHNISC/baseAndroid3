package com.example.basemvvm3.fragment.sub

import androidx.lifecycle.ViewModel
import com.example.basemvvm3.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class SubFragmentModule  {

    @ContributesAndroidInjector
    internal abstract fun contributeSubFragment(): SubFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSubFragment2(): SubFragment2

    @Binds
    @IntoMap
    @ViewModelKey(SubFragment2ViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: SubFragment2ViewModel): ViewModel
}