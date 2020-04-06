package com.example.basemvvm3.classes.data

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("albumID")
    val albumID: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?
)