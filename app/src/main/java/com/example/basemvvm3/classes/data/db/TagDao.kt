package com.example.basemvvm3.classes.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TagDao {
    @Query("SELECT * FROM tagTable ORDER BY name COLLATE NOCASE ASC")
    fun allTag(): DataSource.Factory<Int, TagEntity>

    @Insert
    fun insert(tag: List<TagEntity>)

    @Insert
    fun insert(tag: TagEntity)

    @Delete
    fun delete(tag: TagEntity)
}