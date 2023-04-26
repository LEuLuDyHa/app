package com.github.leuludyha.domain.model.user.preferences

import com.github.leuludyha.domain.model.library.Mocks.work1984
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFoxPref
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.hamcrest.Matchers.`is` as Is

class WorkPreferenceTest {

    @Test
    fun fieldsAreProperlyAccessed() {
        assertThat(workMrFoxPref, Is(workMrFoxPref))
        assertThat(workMrFoxPref.Id(), Is(workMrFoxPref.work.id))
        assertThat(workMrFoxPref.work, Is(workMrFox))
        assertThat(workMrFoxPref.readingState, Is(WorkPreference.ReadingState.READING))
        assertThat(workMrFoxPref.possessed, Is(true))
        assertThat(workMrFoxPref.rating, Is(null))
    }

    @Test
    fun readingStateToStringDisplaysDesiredText() {
        assertThat(WorkPreference.ReadingState.INTERESTED.toString(), Is("Interested"))
        assertThat(WorkPreference.ReadingState.READING.toString(), Is("Currently Reading..."))
        assertThat(WorkPreference.ReadingState.FINISHED.toString(), Is("Finished"))
    }

    @Test
    fun getPossessionStringDisplaysDesiredText() {
        assertThat(
            WorkPreference(
                work1984, WorkPreference.ReadingState.FINISHED, true
            ).getPossessionString(), Is("Somewhere At Home")
        )
        assertThat(
            WorkPreference(
                work1984, WorkPreference.ReadingState.FINISHED, false
            ).getPossessionString(), Is("Not Possessed Anymore")
        )
        assertThat(
            WorkPreference(
                work1984, WorkPreference.ReadingState.READING, false
            ).getPossessionString(), Is("Not Yet Acquired")
        )
    }
}