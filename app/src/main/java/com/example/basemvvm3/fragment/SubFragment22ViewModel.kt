package com.example.basemvvm3.fragment

import androidx.lifecycle.ViewModel
import com.example.basemvvm3.classes.repository.PhotoRepository
import javax.inject.Inject

class SubFragment22ViewModel @Inject constructor(
    private val repo: PhotoRepository
) : ViewModel() {

}