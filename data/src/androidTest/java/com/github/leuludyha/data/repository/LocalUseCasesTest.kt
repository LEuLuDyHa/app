package com.github.leuludyha.data.repository

import com.github.leuludyha.data.RequiringLibraryRepositoryTest
import com.github.leuludyha.data.TestUtils.author1
import com.github.leuludyha.data.TestUtils.edition1
import com.github.leuludyha.data.TestUtils.work1
import com.github.leuludyha.domain.useCase.GetAuthorLocallyUseCase
import com.github.leuludyha.domain.useCase.GetEditionLocallyUseCase
import com.github.leuludyha.domain.useCase.GetWorkLocallyUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

/*
 * I couldn't write those tests at the domain layer because I need to instantiate an API and
 * a database etc, which are not available at that layer.
 *
 * Moreover I didn't write any RepositoryTest because every method of the repository should be
 * covered by UseCases.
 */

class LocalUseCasesTest: RequiringLibraryRepositoryTest() {

    @Test
    fun getWorkLocallyUseCaseGivesCorrectResult() = runBlocking {
        assertThat(GetWorkLocallyUseCase(libraryRepository)(work1.workId).first())
            .isEqualTo(work1.toModel(libraryDao))
    }

    @Test
    fun getEditionLocallyUseCaseGivesCorrectResult() = runBlocking {
        assertThat(GetEditionLocallyUseCase(libraryRepository)(edition1.editionId).first())
            .isEqualTo(edition1.toModel(libraryDao))
    }

    @Test
    fun getAuthorLocallyUseCaseGivesCorrectResult() = runBlocking {
        assertThat(GetAuthorLocallyUseCase(libraryRepository)(author1.authorId).first())
            .isEqualTo(author1.toModel(libraryDao))
    }
}