package com.example.basemvvm3.classes.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tagTable")
data class TagEntity(
    @PrimaryKey(autoGenerate = true) val id: Int, val name: String
)