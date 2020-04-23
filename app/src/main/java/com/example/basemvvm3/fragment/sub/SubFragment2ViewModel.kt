package com.example.basemvvm3.fragment.sub

import androidx.lifecycle.*
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.classes.repository.PhotoRepository
import com.example.basemvvm3.helper.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SubFragment2ViewModel @Inject constructor(
    private val repo: PhotoRepository
) : ViewModel(), SubFragment2EventListener {

    val resultListPhoto: LiveData<Result<List<PhotoItem>>> = liveData {
        emit(Result.Loading)
        emit(repo.getPhoto())
    }

    val resultListPhoto2: LiveData<Result<List<PhotoItem>>> = MutableLiveData()

    fun loadPhoto() = viewModelScope.launch(Dispatchers.IO) {
        repo.getPhoto().apply {
            val mutableLiveData = resultListPhoto2 as MutableLiveData

            mutableLiveData.postValue(Result.Loading)
            mutableLiveData.postValue(this)
        }
    }

    override fun onItemClick(photo: PhotoItem) {
        Timber.e("photo: ${photo.title} clicked")
    }
}

interface SubFragment2EventListener {
    fun onItemClick(photo: PhotoItem)
}