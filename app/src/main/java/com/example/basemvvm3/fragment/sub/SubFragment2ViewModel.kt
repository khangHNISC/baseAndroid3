package com.example.basemvvm3.fragment.sub

import androidx.lifecycle.*
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.classes.repository.PhotoRepository
import com.example.basemvvm3.helper.Event
import com.example.basemvvm3.helper.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SubFragment2ViewModel @Inject constructor(
    private val repo: PhotoRepository
) : ViewModel(), SubFragment2EventListener {

    val isLoading: LiveData<Boolean>

    private val _resultListPhoto2 = MutableLiveData<Result<List<PhotoItem>>>()

    private val _photoDataUI = MediatorLiveData<List<PhotoItem>>()

    val photoDataUI: LiveData<List<PhotoItem>>
        get() =  _photoDataUI

    private val _errorMessage = MediatorLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = _errorMessage

    init {
        isLoading = _resultListPhoto2.map { it is Result.Loading }

        _photoDataUI.addSource(_resultListPhoto2) {
            val data = (it as? Result.Success)?.data ?: return@addSource
            _photoDataUI.value = data
        }

        _errorMessage.addSource(_resultListPhoto2) { result ->
            if (result is Result.Error) {
                _errorMessage.value = Event(content = result.exception.message ?: "Error")
            }
        }
    }

    fun loadPhoto() = viewModelScope.launch(Dispatchers.IO) {
        repo.getPhoto().apply {
            _resultListPhoto2.postValue(Result.Loading)
            _resultListPhoto2.postValue(this)
        }
    }

    override fun onItemClick(photo: PhotoItem) {
        Timber.e("photo: ${photo.title} clicked")
    }
}

interface SubFragment2EventListener {
    fun onItemClick(photo: PhotoItem)
}