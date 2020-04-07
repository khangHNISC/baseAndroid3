package com.example.basemvvm3.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basemvvm3.classes.data.Photo
import com.example.basemvvm3.classes.repository.PhotoRepository
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val repo: PhotoRepository
) : ViewModel() {

    private val _listPhoto = MutableLiveData<List<Photo>>()

    val listPhoto: LiveData<List<Photo>>
        get() = _listPhoto

    private val _numberOfClick = MutableLiveData<Int>()

    val numberOfClick: LiveData<Int>
            get() = _numberOfClick

    private var num = 0

    fun incrementBy1(){
        _numberOfClick.postValue(num++)
    }
}
