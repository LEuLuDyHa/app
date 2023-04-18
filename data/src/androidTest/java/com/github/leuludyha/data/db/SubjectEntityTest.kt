package com.github.leuludyha.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class SubjectEntityTest {

    lateinit var libraryDao: LibraryDao

    @Before
    fun setup() {
        val libraryDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LibraryDatabase::class.java
        ).allowMainThreadQueries().build()
        libraryDao = libraryDatabase.libraryDao()
    }

    @Test
    fun toModelReturnsTheExpectedResult() {
        val subjectEntity = SubjectEntity("subject")
        assertThat(subjectEntity.toModel(libraryDao)).isEqualTo("subject")
    }
}