package com.example.basemvvm3.classes.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.classes.service.PhotoService

class PhotoDataSourceFactory(
    private val photoService: PhotoService
) : DataSource.Factory<String, PhotoItem>() {
    val sourceLiveData = MutableLiveData<ItemKeyedPhotoDataSource>()
    override fun create(): DataSource<String, PhotoItem> {
        val source = ItemKeyedPhotoDataSource(photoService)
        sourceLiveData.postValue(source)
        return source
    }
}