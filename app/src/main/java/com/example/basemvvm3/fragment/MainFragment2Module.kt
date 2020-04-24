package com.example.basemvvm3.fragment

import androidx.lifecycle.ViewModel
import com.example.basemvvm3.di.ViewModelKey
import com.example.basemvvm3.di.scope.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class MainFragment2Module {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment2(): MainFragment2

/*    @Binds
    @IntoMap
    @ViewModelKey(MainFragment2ViewModel::class)
    internal abstract fun bindMain2ViewModel(viewModel: MainFragment2ViewModel): ViewModel*/
}