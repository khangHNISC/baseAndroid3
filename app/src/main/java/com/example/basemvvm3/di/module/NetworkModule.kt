package com.example.basemvvm3.di.module

import com.example.basemvvm3.classes.repository.PhotoRepository
import com.example.basemvvm3.classes.repository.PhotoRepositoryImplement
import com.example.basemvvm3.classes.service.PhotoService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().disableHtmlEscaping().create()

    @Provides
    fun provideInterceptor() = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .url(chain.request().url)
            .addHeader("sample-header", "sample-header-value")
            .build()
        return@Interceptor chain.proceed(request)
    }

    @Provides
    fun provideRetrofitClient(interceptor: Interceptor) = OkHttpClient.Builder()
//        .addInterceptor(interceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(API_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(API_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(API_TIME_OUT, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun providePhotoRepository(service: PhotoService): PhotoRepository =
        PhotoRepositoryImplement(service)

    @Provides
    fun providesPhotoService(retrofit: Retrofit): PhotoService =
        retrofit.create(PhotoService::class.java)

    companion object {
        private const val API_TIME_OUT = 30L
        private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}