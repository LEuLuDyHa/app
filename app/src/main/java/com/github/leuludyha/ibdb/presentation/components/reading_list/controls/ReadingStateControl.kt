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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.WorkPreference
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

@Composable
fun ReadingStateControl(
    work: Work,
    viewModel: ReadingStateControlViewModel = hiltViewModel()
) {
    val userPreferences = viewModel.userPreferences

    val (liked, setLiked) = remember {
        mutableStateOf(
            userPreferences.workPreferences.containsKey(work.Id())
        )
    }
    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    fun onLikeButtonClicked(like: Boolean) {
        setLiked(like)

        if (!like) {
            userPreferences.workPreferences.remove(work.Id())
        } else {
            userPreferences.workPreferences[work.Id()] = WorkPreference(
                work, WorkPreference.ReadingState.INTERESTED, false
            )
        }
    }

    fun setReadingState(readingState: WorkPreference.ReadingState) {
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
            onClick = { onLikeButtonClicked(!liked) }, colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            if (!liked) {
                Icon(Icons.Filled.FavoriteBorder, stringResource(id = R.string.book_like))
            } else {
                Icon(Icons.Filled.Favorite, stringResource(id = R.string.book_dislike))
            }
        }

        if (liked) {
            Column {
                Button(
                    onClick = { setExpanded(!expanded) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    userPreferences.workPreferences[work.Id()]?.let {
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
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IBDBTheme {
        ReadingStateControl(Mocks.work1984)
    }
}