package com.example.basemvvm3.fragment.sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.basemvvm3.classes.data.Listing
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.classes.repository.PhotoRepository
import javax.inject.Inject

class SubFragment23ViewModel @Inject constructor(
    private val repo: PhotoRepository
) : ViewModel() {

    private val repoResult: LiveData<Listing<PhotoItem>> = liveData {
        emit(repo.photosByPage(5))
    }

    val photoDataUI = repoResult.switchMap { it.pagedList }

    //handle swipe refresh
    val refreshState = repoResult.switchMap { it.refreshState }

    val netWorkState = repoResult.switchMap { it.networkState }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        repoResult.value?.retry?.invoke()
    }

    override fun onCleared() {
        super.onCleared()
        repoResult.value?.clearCoroutineJobs?.invoke()
    }
}