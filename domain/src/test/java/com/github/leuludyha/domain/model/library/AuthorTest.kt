package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.library.Mocks.authorGeorgeOrwell
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AuthorTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fieldsAreCorrectlyAccessed() = runTest {
        assertThat(authorRoaldDahl).isEqualTo(authorRoaldDahl)
        assertThat(authorRoaldDahl).isNotEqualTo(1)
        assertThat(authorRoaldDahl).isNotEqualTo(authorGeorgeOrwell)
        assertThat(authorRoaldDahl.id).isEqualTo("OL34184A")
        assertThat(authorRoaldDahl.name).isEqualTo("Roald Dahl")
        assertThat(authorRoaldDahl.birthDate).isEqualTo("13 September 1916")
        assertThat(authorRoaldDahl.deathDate).isEqualTo("23 November 1990")
        assertThat(authorRoaldDahl.works.first()).isEqualTo(listOf(workMrFox))
        assertThat(authorRoaldDahl.covers.first()).isEqualTo(
            listOf(
                9395323L,
                9395316L,
                9395314L,
                9395313L,
                6287214L
            ).map { Cover(it) })
        assertThat(authorRoaldDahl.Id()).isEqualTo(authorRoaldDahl.id)
    }

    @Test
    fun toStringIsAuthorNameWhenNameNotNull() =
        assertThat(authorRoaldDahl.toString()).isEqualTo(authorRoaldDahl.name)

    @Test
    fun toStringIsUnknownAuthorWhenNameNull() =
        assertThat(authorRoaldDahl.copy(name = null).toString()).isEqualTo("Unknown author")

    @Test
    fun hashCodeIsOnlyOnId() {
        assertThat(authorRoaldDahl.hashCode()).isEqualTo(authorRoaldDahl.id.hashCode())
    }
}