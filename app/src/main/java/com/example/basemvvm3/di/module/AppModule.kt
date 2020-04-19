package com.example.basemvvm3.di.module

import android.content.ClipboardManager
import android.content.Context
import android.net.wifi.WifiManager
import com.example.basemvvm3.classes.AppApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: AppApplication): Context {
        return application.applicationContext
    }

    //TODO: put shared preference here

    @Provides
    fun provideWifiManager(context: Context): WifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @Provides
    fun provideClipBoardManager(context: Context): ClipboardManager =
        context.applicationContext.getSystemService((Context.CLIPBOARD_SERVICE)) as ClipboardManager
}