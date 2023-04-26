package com.github.leuludyha.domain.model

import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.edition1984
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class EditionTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fieldsAreCorrectlyAccessed() = runTest {
        assertThat(editionMrFox).isEqualTo(editionMrFox)
        assertThat(editionMrFox).isNotEqualTo(workMrFox)
        assertThat(editionMrFox).isNotEqualTo(edition1984)
        assertThat(editionMrFox.id).isEqualTo("OL44247403M")
        assertThat(editionMrFox.isbn13).isEqualTo("9780142418222")
        assertThat(editionMrFox.isbn10).isNull()
        assertThat(editionMrFox.title).isEqualTo("Fantastic Mr. Fox")
        assertThat(editionMrFox.authors.first()).isEqualTo(listOf(authorRoaldDahl))
        assertThat(editionMrFox.works.first()).isEqualTo(listOf(workMrFox))
        assertThat(editionMrFox.covers.first()).isEqualTo(listOf(Cover(13269612)))
    }
}