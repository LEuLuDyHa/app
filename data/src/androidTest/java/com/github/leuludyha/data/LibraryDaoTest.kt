package com.github.leuludyha.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.data.db.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LibraryDaoTest {
    private lateinit var libraryDao: LibraryDao
    private lateinit var db: LibraryDB

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LibraryDB::class.java
        ).allowMainThreadQueries().build()

        libraryDao = db.libraryDao()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {

        val work1 = WorkEntity("work1", "MyWork1")
        val work2 = WorkEntity("work2", "MyWork2")
        val work3 = WorkEntity("work3", "MyWork3")

        val author1 = AuthorEntity("author1", null, "Author1", null, null)
        val author2 = AuthorEntity("author2", null, "Author2", null, null)
        val author3 = AuthorEntity("author3", null, "Author3", null, null)

        runBlocking { libraryDao.insert(work1) }
        runBlocking { libraryDao.insert(work2) }
        runBlocking { libraryDao.insert(work3) }

        runBlocking { libraryDao.insert(author1) }
        runBlocking { libraryDao.insert(author2) }
        runBlocking { libraryDao.insert(author3) }

        runBlocking { libraryDao.insert(WorkAuthorCrossRef(work1.workId, author1.authorId)) }
        runBlocking { libraryDao.insert(WorkAuthorCrossRef(work1.workId, author2.authorId)) }
        runBlocking { libraryDao.insert(WorkAuthorCrossRef(work1.workId, author3.authorId)) }

        runBlocking { println("### ${libraryDao.getWorkWithAuthors(work1.workId)}") }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

}