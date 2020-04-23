package com.example.basemvvm3.classes.repository

import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.classes.service.PhotoService
import com.example.basemvvm3.helper.Result
import java.lang.Exception
import javax.inject.Inject

interface PhotoRepository {
    suspend fun getPhoto(): Result<List<PhotoItem>>
}

class PhotoRepositoryImplement @Inject constructor(private val photoService: PhotoService) :
    PhotoRepository {

    override suspend fun getPhoto(): Result<List<PhotoItem>> {
        val response = photoService.getAllPhotoAsync().await()
        return if (response.isSuccessful) {
            Result.Success(response.body()?.map {
                PhotoItem(
                    it.albumID ?: "",
                    it.id ?: "",
                    it.title ?: "",
                    it.url ?: "",
                    it.thumbnailUrl ?: ""
                )
            } ?: emptyList())
        } else {
            Result.Error(Exception("Server Error"))
        }
    }
}