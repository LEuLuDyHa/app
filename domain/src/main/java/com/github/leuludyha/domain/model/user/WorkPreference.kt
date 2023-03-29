package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.interfaces.Keyed
import com.github.leuludyha.domain.model.library.Work

class WorkPreference(
    val work: Work,
    var readingState: ReadingState,
    val possessed: Boolean,
) : Keyed {

    enum class ReadingState {
        /** The user has finished reading this book */
        FINISHED,

        /** The user is currently reading this book */
        READING,

        /** The user is interested in reading the book */
        INTERESTED;

        override fun toString(): String {
            return when (this) {
                FINISHED -> "Finished"
                READING -> "Currently Reading..."
                INTERESTED -> "Interested"
            }
        }
    }

    override fun getId(): String {
        return this.work.getId(); }

    /**
     * @return A string displaying a text depending on whether or not
     * the user has acquired this book
     */
    fun getPossessionString(): String {
        return when (this.possessed) {
            true -> "Somewhere At Home"
            false -> {
                if (this.readingState == ReadingState.FINISHED) {
                    "Not Possessed Anymore"
                } else {
                    "Not Yet Acquired"
                }
            }
        }
    }
}
