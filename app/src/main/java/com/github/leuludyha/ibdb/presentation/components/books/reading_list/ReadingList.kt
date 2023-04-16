package com.github.leuludyha.ibdb.presentation.components.books.reading_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AddLocation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.user.UserPreferences
import com.github.leuludyha.domain.model.user.WorkPreference
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.books.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

/**
 * The reading list of the user, it takes the user preferences and display
 * what the user wishes to read/ has finished reading/ is currently reading
 * and whether or not they possess the book
 */
@Composable
fun ReadingList(
    navController: NavHostController,
    preferences: UserPreferences,
) {
    // Use the ItemList component to display a list of works horizontally
    ItemList(
        // TODO Maybe change this to be sorted using user rating/ other factors
        values = preferences.workPreferences.values.toList(),
        modifier = Modifier.wrapContentSize()
    ) { preference ->
        Column(modifier = Modifier.wrapContentHeight()) {
            // Each item of the reading list will be a mini book view
            MiniBookView(
                work = preference.work,
                // Do not display the work's subjects, too much info is not needed
                displaySubjects = false,
                // On click, redirect to the BookDetails screen on this book
                onClick = { clickedWork ->
                    navController.navigate(Screen.BookDetails.passBookId(clickedWork.Id()))
                }, footer = {
                    // As a footer, pass a pair of icons informing the user about its
                    // progress on the reading of this book and whether
                    // the user possess it at home or not
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        PossessionIcon(possessed = preference.possessed, navController)
                        ReadingStateIcon(readingState = preference.readingState)
                    }
                })

        }
    }
}

/**
 * Display whether this work is possessed by the user or not
 */
@Composable
private fun PossessionIcon(
    possessed: Boolean,
    navController: NavHostController
) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        onClick = { navController.navigate(Screen.FindBook.route) },
    ) {
        when (possessed) {
            true -> Icon(Icons.Filled.WhereToVote, "")
            false -> Icon(Icons.Outlined.AddLocation, "")
        }
    }
}

/**
 * Display whether the user is either
 * - Interested
 * - Currently Reading
 * - Has finished
 * This work
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReadingStateIcon(
    readingState: WorkPreference.ReadingState
) {
    AssistChip(
        colors = AssistChipDefaults.assistChipColors(
            labelColor = MaterialTheme.colorScheme.primary
        ),
        onClick = { /* Nothing */ },
        leadingIcon = {
            when (readingState) {
                WorkPreference.ReadingState.INTERESTED -> {
                    Icon(Icons.Filled.Bookmark, "")
                }
                WorkPreference.ReadingState.READING -> {
                    Icon(Icons.Filled.Cached, "")
                }
                WorkPreference.ReadingState.FINISHED -> {
                    Icon(Icons.Filled.DoneAll, "")
                }
            }
        },
        label = {
            Text(
                text = readingState.toString(),
                style = MaterialTheme.typography.labelSmall
            )
        }
    )
}

@Preview
@Composable
fun DefaultPreview() {
    // Create an empty instance of user preference
    val preferences = UserPreferences()
    // Add the Ninety Eighty-Four work as a finished and non-possessed work
    preferences.addPreference(
        WorkPreference(
            Mocks.work1984,
            WorkPreference.ReadingState.FINISHED,
            false
        )
    )
    preferences.addPreference(
        WorkPreference(
            Mocks.workLaFermeDesAnimaux,
            WorkPreference.ReadingState.READING,
            true
        )
    )
    // Preview of the reading list
    IBDBTheme {
        ReadingList(
            navController = rememberNavController(),
            preferences = preferences
        )
    }
}