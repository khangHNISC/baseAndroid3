package com.example.basemvvm3.classes.repository

import com.example.basemvvm3.classes.data.Photo
import com.example.basemvvm3.classes.service.PhotoService
import retrofit2.Response
import javax.inject.Inject

interface PhotoRepository {
    suspend fun getPhoto(): Response<List<Photo>>
}

class PhotoRepositoryImplement @Inject constructor(private val photoService: PhotoService) :
    PhotoRepository {

    override suspend fun getPhoto(): Response<List<Photo>> {
        return photoService.getAllPhotoAsync().await()
    }
}