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

    fun getPhotoPaging(pageSize: Int): Listing<PhotoItem>
}

class PhotoRepositoryImplement @Inject constructor(private val photoService: PhotoService) :
    PhotoRepository {

    //service return deferred, suspend repo return data, vm creates coroutine
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


    //in VM assigned directly to LiveData -> best way.
    // service return deferred, repo return live data , vm wire live data
   /* override fun getPhoto(): LiveData<Result<List<PhotoItem>>> = liveData {
        emit(Result.Loading)
        val response = photoService.getAllPhotoAsync().await()
        if (response.isSuccessful) {
            emit(Result.Success(response.body()?.map {
                PhotoItem(
                    it.albumID ?: "",
                    it.id ?: "",
                    it.title ?: "",
                    it.url ?: "",
                    it.thumbnailUrl ?: ""
                )
            } ?: emptyList()))
        } else {
            emit(Result.Error(Exception("Server Error")))
        }
    }*/

    override fun getPhotoPaging(pageSize: Int): Listing<PhotoItem> {
        //Data Source Factory from Data Layer
        val sourceFactory = PhotoDataSourceFactory(photoService)

        val livePagedList = sourceFactory.toLiveData(
            config = Config(
                pageSize = pageSize,
                enablePlaceholders = false,
                initialLoadSizeHint = pageSize
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