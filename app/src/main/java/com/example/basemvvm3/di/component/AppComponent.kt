package com.example.basemvvm3.di.component

import com.example.basemvvm3.classes.AppApplication
import com.example.basemvvm3.di.module.ActivityBindingModule
import com.example.basemvvm3.di.module.AppModule
import com.example.basemvvm3.di.module.NetworkModule
import com.example.basemvvm3.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityBindingModule::class,
    AppModule::class,
    NetworkModule::class,
    ViewModelModule::class
])
interface AppComponent: AndroidInjector<AppApplication> {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun bindApplication(App: AppApplication): Builder

        fun build(): AppComponent
    }
}