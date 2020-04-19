package com.example.basemvvm3.di.module

import android.content.Context
import com.example.basemvvm3.classes.data.db.AppDatabase
import com.example.basemvvm3.classes.repository.TagRepository
import com.example.basemvvm3.classes.repository.TagRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(context: Context): AppDatabase = AppDatabase.buildDatabase(context)

    @Singleton
    @Provides
    fun provideTagDao(appDatabase: AppDatabase) = appDatabase.tagDao()

    @Singleton
    @Provides
    fun provideTagRepo(appDatabase: AppDatabase): TagRepository = TagRepositoryImpl(appDatabase)
}