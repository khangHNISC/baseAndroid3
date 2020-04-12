package com.example.basemvvm3.classes.mapper

import com.example.basemvvm3.classes.data.Photo
import com.example.basemvvm3.classes.data.PhotoItem

fun Photo.toPhotoItem(): PhotoItem {
    return PhotoItem(albumID ?: "", id ?: "", title ?: "", url ?: "", thumbnailUrl ?: "")
}