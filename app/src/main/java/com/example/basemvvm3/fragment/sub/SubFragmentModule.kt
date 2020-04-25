package com.example.basemvvm3.fragment.sub

import androidx.lifecycle.ViewModel
import com.example.basemvvm3.di.ViewModelKey
import com.example.basemvvm3.di.scope.FragmentScoped
import com.example.basemvvm3.fragment.Fragment2ViewPoolModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class SubFragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSubFragment(): SubFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSubFragment2(): SubFragment2

    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [Fragment2ViewPoolModule::class]
    )
    internal abstract fun contributeSubFragment21(): SubFragment21

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSubFragment22(): SubFragment22

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSubFragment23(): SubFragment23

    @Binds
    @IntoMap
    @ViewModelKey(SubFragment2ViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: SubFragment2ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubFragment21ViewModel::class)
    internal abstract fun bindMainViewModel21(viewModel: SubFragment21ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubFragment22ViewModel::class)
    internal abstract fun bindMainViewModel22(viewModel: SubFragment22ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubFragment23ViewModel::class)
    internal abstract fun bindMainViewModel23(viewModel: SubFragment23ViewModel): ViewModel
}