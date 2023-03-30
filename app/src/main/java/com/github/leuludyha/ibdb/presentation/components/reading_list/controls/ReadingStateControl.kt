package com.github.leuludyha.ibdb.presentation.components.reading_list.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.UserPreferences
import com.github.leuludyha.domain.model.user.WorkPreference
import com.github.leuludyha.domain.util.TestTag
import com.github.leuludyha.domain.util.testTag
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

object TestTags {
    val readingStateController = TestTag("reading-state-dropdown")
    val likeButton = TestTag("like-button")
}

/**
 * On the book details page of a work, allow a user to set if they :
 * - are interested in reading the book
 * - are currently reading the book
 * - have finished reading the book
 */
@Composable
private fun ReadingStateControl(
    work: Work,
    userPreferences: UserPreferences,
) {
    // Whether this work is liked by the user (added to its reading list)
    val (liked, setLiked) = remember {
        mutableStateOf(
            // Initiate it to the userPreferences
            userPreferences.workPreferences.containsKey(work.Id())
        )
    }
    // Whether the menu button is expanded (visible) or not
    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    fun onLikeButtonClicked(like: Boolean) {
        setLiked(like)

        // If the user dislikes the work, remove it from its preferences
        if (!like) {
            userPreferences.workPreferences.remove(work.Id())
        // Otherwise, add it in its preferences, annotating it as interested and not possessed
        } else {
            userPreferences.workPreferences[work.Id()] = WorkPreference(
                work, WorkPreference.ReadingState.INTERESTED, false
            )
        }
    }

    fun setReadingState(readingState: WorkPreference.ReadingState) {
        // Set the reading state to the one set by the user using the menu button
        userPreferences.workPreferences[work.Id()]?.let {
            it.readingState = readingState
        }
        setExpanded(false)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onLikeButtonClicked(!liked) },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.testTag(TestTags.likeButton),
        ) {
            if (!liked) {
                Icon(Icons.Filled.FavoriteBorder, stringResource(id = R.string.book_like))
            } else {
                Icon(Icons.Filled.Favorite, stringResource(id = R.string.book_dislike))
            }
        }

        // Only display reading state controls if the work is "liked"
        if (liked) {
            Column {
                Button(
                    onClick = { setExpanded(!expanded) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    modifier = Modifier.testTag(TestTags.readingStateController.tag)
                ) {
                    // Display the current reading state in a button,
                    // Once it is clicked, a menu will appear with all states
                    userPreferences.workPreferences[work.Id()]?.let {
                        Text(
                            text = it.readingState.toString(),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                // Menu of all possible reading state
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { setExpanded(false) }
                ) {
                    WorkPreference.ReadingState.values().map {
                        DropdownMenuItem(text = {
                            Text(text = it.toString())
                        }, onClick = {
                            setReadingState(it)
                        })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IBDBTheme {
        ReadingStateControl(
            Mocks.work1984,
            Mocks.userPreferences
        )
    }
}