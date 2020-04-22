package com.example.basemvvm3.fragment.sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basemvvm3.classes.data.PersonItem
import com.example.basemvvm3.helper.EventAction
import javax.inject.Inject

class SubFragment21ViewModel @Inject constructor(): ViewModel(), EventAction{
    private val _navigateToPersonDetail = MutableLiveData<PersonItem>()
    
    val navigateToPersonDetail: LiveData<PersonItem>
            get() = _navigateToPersonDetail
    
    override fun openEventDetail(person: PersonItem) {
        _navigateToPersonDetail.value = person
    }
}