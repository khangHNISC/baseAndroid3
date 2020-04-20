package com.example.basemvvm3.classes.repository

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.basemvvm3.classes.data.db.AppDatabase
import com.example.basemvvm3.classes.data.db.TagEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TagRepository {

    fun getAllUser(): LiveData<PagedList<TagEntity>>

    suspend fun insertTag(tagName: String)

    suspend fun deleteTag(tag: TagEntity)
}

class TagRepositoryImpl @Inject constructor(
    private val db: AppDatabase
) : TagRepository {

    override fun getAllUser() = db.tagDao().allTag().toLiveData(
        Config(pageSize = 60, enablePlaceholders = false, maxSize = 200)
    )

    override suspend fun insertTag(tagName: String) {
        withContext(Dispatchers.IO) {
            db.tagDao().insert(TagEntity(id = 0, name = tagName))
        }
    }

    override suspend fun deleteTag(tag: TagEntity) {
        withContext(Dispatchers.IO) {
            db.tagDao().delete(tag)
        }
    }
}