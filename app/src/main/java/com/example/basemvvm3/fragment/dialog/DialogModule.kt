package com.example.basemvvm3.fragment.dialog

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DialogModule {

    @ContributesAndroidInjector
    internal abstract fun contributeFragmentCenterDialog(): GeneralCenterDialogWith2Buttons

    @ContributesAndroidInjector
    internal abstract fun contributeFragmentBottomDialog(): GeneralBottomDialog
}