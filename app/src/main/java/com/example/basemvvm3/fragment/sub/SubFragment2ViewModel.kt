package com.example.basemvvm3.fragment.sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.classes.mapper.toPhotoItem
import com.example.basemvvm3.classes.repository.PhotoRepository
import kotlinx.coroutines.async
import javax.inject.Inject

class SubFragment2ViewModel @Inject constructor(
    private val repo: PhotoRepository
) : ViewModel() {

    private val _listPhoto = MutableLiveData<List<PhotoItem>>()

    val listPhoto: LiveData<List<PhotoItem>>
        get() = _listPhoto

    fun getPhoto() {
        viewModelScope.async {
            val response = repo.getPhoto()
            if (response.isSuccessful) {
                _listPhoto.postValue(response.body()?.map { it.toPhotoItem() })
            }else{
                //post error
                _listPhoto.postValue(arrayListOf())
            }
        }
    }
}