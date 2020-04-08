package com.example.basemvvm3.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor() : ViewModel(){
    private val _currentFragmentTag = MutableLiveData<String>()

    val currentFragmentTag: LiveData<String>
            get() = _currentFragmentTag

    fun notifyMainFragment2(nameFragment: String){
        _currentFragmentTag.postValue(nameFragment)
    }
}