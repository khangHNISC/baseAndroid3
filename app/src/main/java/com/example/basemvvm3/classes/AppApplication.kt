package com.example.basemvvm3.classes

import android.app.Application
import com.example.basemvvm3.BuildConfig
import com.example.basemvvm3.di.component.AppComponent
import com.example.basemvvm3.di.component.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class AppApplication : DaggerApplication() {

    companion object {
        lateinit var INSTANCE: Application
    }

    init {
        INSTANCE = this
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        AndroidThreeTen.init(this)
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().bindApplication(this).build()
        return appComponent
    }
}