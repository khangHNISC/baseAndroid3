package com.example.basemvvm3.classes.repository

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.basemvvm3.classes.data.db.AppDatabase
import com.example.basemvvm3.classes.data.db.TagEntity
import javax.inject.Inject

interface TagRepository {

    fun getAllUser(): LiveData<PagedList<TagEntity>>

    fun insertTag(tag: TagEntity)

    fun deleteTag(tag: TagEntity)
}

class TagRepositoryImpl @Inject constructor(
    private val db: AppDatabase
) : TagRepository {

    override fun getAllUser() = db.tagDao().allTag().toLiveData(
        Config(pageSize = 60, enablePlaceholders = false, maxSize = 200)
    )

    override fun insertTag(tag: TagEntity) {
        db.runInTransaction {
            db.tagDao().insert(tag)
        }
    }

    override fun deleteTag(tag: TagEntity) {
        db.runInTransaction {
            db.tagDao().delete(tag)
        }
    }
}