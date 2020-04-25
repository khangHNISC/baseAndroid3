package com.example.basemvvm3.classes.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.helper.Result
import com.example.basemvvm3.classes.service.PhotoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception

class ItemKeyedPhotoDataSource(
    private val photoService: PhotoService
) : ItemKeyedDataSource<String, PhotoItem>() {

    private val completableJob = Job()

    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob) //coroutine scope

    val initialLoad = MutableLiveData<Result<Unit>>()

    val netWorkState = MutableLiveData<Result<Unit>>()

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<PhotoItem>
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            initialLoad.postValue(Result.Loading)
            netWorkState.postValue(Result.Loading)
            try {
                val request = photoService.getPhotoFromPageAsync(
                    after = 0,
                    limit = params.requestedLoadSize
                ).await()
                if (request.isSuccessful) {
                    val items = request.body()?.map {
                        PhotoItem(
                            it.albumID ?: "",
                            it.id ?: "",
                            it.title ?: "",
                            it.url ?: "",
                            it.thumbnailUrl ?: ""
                        )
                    } ?: emptyList<PhotoItem>()

                    initialLoad.postValue(Result.Success(Unit))
                    netWorkState.postValue(Result.Success(Unit))
                    callback.onResult(items)
                } else {
                    initialLoad.postValue(Result.Error(Exception("Server Error")))
                    netWorkState.postValue(Result.Error(Exception("Server Error")))
                }
            } catch (e: IOException) {
                retry = {
                    loadInitial(params, callback)
                }
                initialLoad.postValue(Result.Error(Exception("Server Error")))
                netWorkState.postValue(Result.Error(Exception("Server Error")))
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<PhotoItem>) {
        coroutineScope.launch {
            netWorkState.postValue(Result.Loading)
            try {
                val request = photoService.getPhotoFromPageAsync(
                    after = params.key.toInt(),
                    limit = params.requestedLoadSize
                ).await()
                if (request.isSuccessful) {
                    val items = request.body()?.map {
                        PhotoItem(
                            it.albumID ?: "",
                            it.id ?: "",
                            it.title ?: "",
                            it.url ?: "",
                            it.thumbnailUrl ?: ""
                        )
                    } ?: emptyList<PhotoItem>()

                    retry = null
                    netWorkState.postValue(Result.Success(Unit))
                    callback.onResult(items)
                } else {
                    retry = {
                        loadAfter(params, callback)
                    }
                    netWorkState.postValue(Result.Error(Exception("Server Error")))
                }
            } catch (e: IOException) {
                retry = {
                    loadAfter(params, callback)
                }
                netWorkState.postValue(Result.Error(Exception("Server Error")))
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<PhotoItem>) {
        //ignore
    }

    override fun getKey(item: PhotoItem) = item.id

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            coroutineScope.launch {
                it.invoke()
            }
        }
    }

    fun clearCoroutineJobs() {
        completableJob.cancel()
    }
}