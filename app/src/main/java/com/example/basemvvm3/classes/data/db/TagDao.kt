package com.example.basemvvm3.classes.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TagDao {
    @Query("SELECT * FROM tagTable ORDER BY name COLLATE NOCASE ASC")
    fun allTag(): DataSource.Factory<Int, TagEntity>

    //can do query return LiveData Here -> db update -> notify LiveData
    //-> update observer to it (only need to get List 1)
    @Query("SELECT * FROM tagTable ORDER BY name COLLATE NOCASE ASC")
    fun allTag2(): LiveData<List<TagEntity>>

    @Query("SELECT * FROM tagTable ORDER BY name COLLATE NOCASE DESC LIMIT 1")
    fun getTOP(): TagEntity?

    @Insert
    suspend fun insert(tag: List<TagEntity>)

    @Insert
    suspend fun insert(tag: TagEntity)

    @Delete
    suspend fun delete(tag: TagEntity)
}