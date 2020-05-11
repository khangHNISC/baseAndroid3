package com.example.basemvvm3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.basemvvm3.classes.data.db.AppDatabase
import com.example.basemvvm3.classes.data.db.TagDao
import com.example.basemvvm3.classes.data.db.TagEntity
import com.example.basemvvm3.helper.LiveDataTestUtil
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class DBTest {

    private lateinit var tagDao: TagDao
    private lateinit var db: AppDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        tagDao = db.tagDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGet() {
        val tag = TagEntity(id = 0, name = "khang")
        runBlocking {
            tagDao.insert(tag)
        }
        val tagList = tagDao.allTag2()
        val result = LiveDataTestUtil.getValue(tagList)
        assertEquals(result?.size, 1)
    }
}
