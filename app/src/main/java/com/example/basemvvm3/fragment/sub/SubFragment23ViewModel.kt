package com.example.basemvvm3.fragment.sub

import androidx.lifecycle.ViewModel
import com.example.basemvvm3.classes.repository.PhotoRepository
import javax.inject.Inject

class SubFragment23ViewModel @Inject constructor(
    repo: PhotoRepository
) : ViewModel() {

    private val repoResult = repo.getPhotoPaging(10)

    // LiveData<PageList> taken from Repo
    val photoDataUI = repoResult.pagedList

    //handle swipe refresh
    val refreshState = repoResult.refreshState

    val netWorkState = repoResult.networkState

    fun refresh() {
        repoResult.refresh.invoke()
    }

    fun retry() {
        repoResult.retry.invoke()
    }

    override fun onCleared() {
        super.onCleared()
        repoResult.clearCoroutineJobs.invoke()
    }
}