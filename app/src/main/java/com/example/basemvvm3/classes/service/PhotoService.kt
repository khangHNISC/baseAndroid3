package com.example.basemvvm3.classes.service

import com.example.basemvvm3.classes.data.Photo
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {
    @GET("/photos")
    fun getAllPhotoAsync(): Deferred<Response<List<Photo>>>

    @GET("/photos")
    fun getPhotoFromPageAsync(
        @Query("_start") after: Int,
        @Query("_limit") limit: Int
    ): Deferred<Response<List<Photo>>>
}