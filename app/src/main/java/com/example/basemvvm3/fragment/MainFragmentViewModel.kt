package com.example.basemvvm3.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(): ViewModel() {
    private val _sayHello = MutableLiveData<Int>()

    val sayHello: LiveData<Int>
            get() = _sayHello

    fun setHello(){
        _sayHello.postValue(0)
    }
}