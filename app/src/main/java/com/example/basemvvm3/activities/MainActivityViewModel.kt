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

    private val _currentFragmentTag = MutableLiveData<String>()

    val currentFragmentTag: LiveData<String>
        get() = _currentFragmentTag

    fun notifyMainFragment2(nameFragment: String){
        _currentFragmentTag.postValue(nameFragment)
    }
}
