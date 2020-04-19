package com.example.basemvvm3.fragment.sub

import androidx.lifecycle.ViewModel
import com.example.basemvvm3.classes.repository.TagRepository
import javax.inject.Inject

class SubFragment22ViewModel @Inject constructor(
    tagRepo: TagRepository
) : ViewModel() {

    val tagData = tagRepo.getAllUser()
}