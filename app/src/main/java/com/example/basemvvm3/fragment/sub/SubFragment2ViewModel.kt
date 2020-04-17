package com.example.basemvvm3.fragment.sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.classes.repository.PhotoRepository
import com.example.basemvvm3.helper.Result
import kotlinx.coroutines.async
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class SubFragment2ViewModel @Inject constructor(
    private val repo: PhotoRepository
) : ViewModel(), SubFragment2EventListener {

    private val _resultListPhoto = MutableLiveData<Result<List<PhotoItem>>>()

    val resultListPhoto: LiveData<Result<List<PhotoItem>>>
        get() = _resultListPhoto

    fun getPhoto() {
        _resultListPhoto.postValue(Result.Loading)
        viewModelScope.async {
            val response = repo.getPhoto()
            if (response.isSuccessful) {
                _resultListPhoto.postValue(
                    Result.Success(response.body()?.map {
                        PhotoItem(
                            it.albumID ?: "",
                            it.id ?: "",
                            it.title ?: "",
                            it.url ?: "",
                            it.thumbnailUrl ?: ""
                        )
                    } ?: emptyList())
                )
            } else {
                //post error
                _resultListPhoto.postValue(Result.Error(Exception("Server Error")))
            }
        }
    }

    override fun onItemClick(photo: PhotoItem) {
        Timber.e("photo: ${photo.title} clicked")
    }
}

interface SubFragment2EventListener {
    fun onItemClick(photo: PhotoItem)
}