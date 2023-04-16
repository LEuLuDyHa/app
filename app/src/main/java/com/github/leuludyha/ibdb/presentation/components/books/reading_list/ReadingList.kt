package com.github.leuludyha.ibdb.presentation.components.books.reading_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AddLocation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.user.UserPreferences
import com.github.leuludyha.domain.model.user.WorkPreference
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.books.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingList(
    navController: NavHostController,
    preferences: UserPreferences,
) {
    ItemList(
        // TODO Maybe change this to be sorted using user rating/ other factors
        values = preferences.workPreferences.values.toList(),
        modifier = Modifier.wrapContentHeight()
    ) { preference ->
        Column(modifier = Modifier.wrapContentHeight()) {

            MiniBookView(
                work = preference.work,
                displaySubjects = false,
                onClick = { clickedWork ->
                    navController.navigate(Screen.BookDetails.passBookId(clickedWork.Id()))
                }, footer = {
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