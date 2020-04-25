package com.example.basemvvm3.classes.repository

import androidx.lifecycle.switchMap
import androidx.paging.Config
import androidx.paging.toLiveData
import com.example.basemvvm3.classes.data.Listing
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.classes.paging.PhotoDataSourceFactory
import com.example.basemvvm3.classes.service.PhotoService
import com.example.basemvvm3.helper.Result
import java.lang.Exception
import javax.inject.Inject

interface PhotoRepository {
    suspend fun getPhoto(): Result<List<PhotoItem>>

    fun photosByPage(pageSize: Int): Listing<PhotoItem>
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

    override fun photosByPage(pageSize: Int): Listing<PhotoItem> {
        val sourceFactory = PhotoDataSourceFactory(photoService)

        val livePagedList = sourceFactory.toLiveData(
            config = Config(
                pageSize = pageSize,
                enablePlaceholders = false,
                initialLoadSizeHint = pageSize * 2
            )
        )

        return Listing(
            pagedList = livePagedList,
            refreshState = sourceFactory.sourceLiveData.switchMap {
                it.initialLoad
            },
            networkState = sourceFactory.sourceLiveData.switchMap {
                it.netWorkState
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            clearCoroutineJobs = {
                sourceFactory.sourceLiveData.value?.clearCoroutineJobs()
            }
        )
    }
}