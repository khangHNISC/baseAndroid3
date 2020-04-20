package com.example.basemvvm3.fragment.sub

import androidx.lifecycle.ViewModel
import com.example.basemvvm3.classes.data.db.TagEntity
import com.example.basemvvm3.classes.repository.TagRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SubFragment22ViewModel @Inject constructor(
    private val tagRepo: TagRepository
) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val tagData = tagRepo.getAllUser()

    fun insertTag(tagName: CharSequence) = uiScope.launch { tagRepo.insertTag(tagName.toString()) }

    fun removeTag(tagEntity: TagEntity) = uiScope.launch { tagRepo.deleteTag(tagEntity) }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}