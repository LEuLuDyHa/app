package com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.util.TestTag
import com.github.leuludyha.domain.util.testTag
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.utils.Loading
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
fun ReadingStateControl(
    work: Work,
    viewModel: ReadingStateControlViewModel = hiltViewModel()
) {
    val workPreferences = viewModel.workPreferences.collectAsState(initial = mapOf())

    val (saving, setSaving) = remember { mutableStateOf(false) }

    val liked = workPreferences.value.keys.contains(work.id)

    // Whether the menu button is expanded (visible) or not
    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    fun onLikeButtonClicked(like: Boolean) {
        // If the user dislikes the work, remove it from its preferences
        if (!like) {
            workPreferences.value[work.id]?.let {
                setSaving(true)
                viewModel.deleteWorkPref(it)
                    .invokeOnCompletion { setSaving(false) }
            }
            // Otherwise, add it in its preferences, annotating it as interested and not possessed
        } else {
            setSaving(true)
            viewModel.saveWorkPref(
                WorkPreference(
                    work,
                    WorkPreference.ReadingState.INTERESTED,
                    false
                )
            ).invokeOnCompletion { setSaving(false) }
        }
    }

    fun setReadingState(readingState: WorkPreference.ReadingState) {
        // Set the reading state to the one set by the user using the menu button
        workPreferences.value[work.id]?.let {
            setSaving(true)
            viewModel.saveWorkPref(it.copy(readingState = readingState))
                .invokeOnCompletion { setSaving(false) }
        }
        setExpanded(false)
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!saving) {
            IconButton(
                modifier = Modifier.testTag(TestTags.likeButton),
                onClick = { onLikeButtonClicked(!liked) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                when (liked) {
                    true -> Icon(Icons.Filled.Favorite, stringResource(id = R.string.book_dislike))
                    false -> Icon(
                        Icons.Filled.FavoriteBorder,
                        stringResource(id = R.string.book_like)
                    )
                }
            }

            // Only display reading state controls if the work is "liked"
            if (liked) {
                Column {
                    Button(
                        onClick = { setExpanded(!expanded) },
                        modifier = Modifier.testTag(TestTags.readingStateController),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        // Display the current reading state in a button,
                        // Once it is clicked, a menu will appear with all states
                        workPreferences.value[work.id]?.let {
                            Text(
                                text = it.readingState.toString(),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }

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
        } else {
            Loading()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IBDBTheme {
        ReadingStateControl(Mocks.work1984)
    }
}