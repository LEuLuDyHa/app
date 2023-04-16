package com.github.leuludyha.domain.model.user.preferences

import com.github.leuludyha.domain.model.library.Mocks
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.hamcrest.Matchers.`is` as Is

class WorkPreferencesTest {

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
                Mocks.work1984, WorkPreference.ReadingState.FINISHED, true
            ).getPossessionString(), Is("Somewhere At Home")
        )
        assertThat(
            WorkPreference(
                Mocks.work1984, WorkPreference.ReadingState.FINISHED, false
            ).getPossessionString(), Is("Not Possessed Anymore")
        )
        assertThat(
            WorkPreference(
                Mocks.work1984, WorkPreference.ReadingState.READING, false
            ).getPossessionString(), Is("Not Yet Acquired")
        )
    }
}