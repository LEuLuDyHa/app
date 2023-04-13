package com.github.leuludyha.data.datasource

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.leuludyha.data.db.LibraryDatabase
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.data.repository.datasourceImpl.LibraryLocalDataSourceImpl
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LibraryLocalDataSourceImplTest {

    lateinit var localDataSource: LibraryLocalDataSource

    @Before
    fun setup() {
        val libraryDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LibraryDatabase::class.java
        ).allowMainThreadQueries().build()
        val libraryDao = libraryDatabase.libraryDao()

        localDataSource = LibraryLocalDataSourceImpl(libraryDao)
    }

    @Test
    fun getWorkGivesCorrectResultAfterSavingIt() = runBlocking {
        localDataSource.saveWork(workMrFox)
        val data = localDataSource.getWork(workMrFox.id).first()
        assertThat(data).isEqualTo(workMrFox)
    }

    @Test
    fun getEditionGivesCorrectResultAfterSavingIt() = runBlocking {
        localDataSource.saveEdition(editionMrFox)
        val data = localDataSource.getEdition(editionMrFox.id).first()
        assertThat(data).isEqualTo(editionMrFox)
    }

    @Test
    fun getEditionByISBNGivesCorrectResultAfterSavingIt() = runBlocking {
        localDataSource.saveEdition(editionMrFox)
        val data = localDataSource.getEditionByISBN(editionMrFox.isbn13!!).first()
        assertThat(data).isEqualTo(editionMrFox)
    }

    @Test
    fun getAuthorGivesCorrectResultAfterSavingIt() = runBlocking {
        localDataSource.saveAuthor(authorRoaldDahl)
        val data = localDataSource.getAuthor(authorRoaldDahl.id).first()
        assertThat(data).isEqualTo(authorRoaldDahl)
    }

    @Test
    fun getCoverGivesCorrectResultAfterSavingAuthor() = runBlocking {
        localDataSource.saveAuthor(authorRoaldDahl)
        val data = localDataSource.getCover(authorRoaldDahl.covers.first()[0].id).first()
        assertThat(data).isEqualTo(authorRoaldDahl.covers.first()[0])
    }
}