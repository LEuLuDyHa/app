package com.github.leuludyha.ibdb.presentation.components.book_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.github.leuludyha.domain.model.library.CoverSize
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.util.toText
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.Orientation

@Composable
fun MiniBookView(
    work: Work,
    onClick: (work: Work) -> Unit,
    orientation: Orientation = Orientation.Vertical,
    footer: (@Composable (work: Work) -> Unit)? = null,
    displaySubjects: Boolean = true,
) {

    when (orientation) {
        Orientation.Vertical -> VerticalBookView(
            work,
            onClick,
            footer,
            displaySubjects)
        Orientation.Horizontal -> HorizontalBookView(
            work,
            onClick,
            footer,
            displaySubjects
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerticalBookView(
    work: Work,
    onClick: (work: Work) -> Unit,
    footer: (@Composable (work: Work) -> Unit)? = null,
    displaySubjects: Boolean,
) {
    val covers = work.covers.collectAsState(initial = listOf())
    val authors = work.authors.collectAsState(initial = listOf())
    val subjects = work.subjects.collectAsState(initial = listOf())

    ElevatedCard(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp),
        // On click, navigate to the book's details screen using its id
        onClick = { onClick(work) }
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            // Display the book's thumbnail
            Image(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                painter = rememberImagePainter(
                    data =  covers.value.firstOrNull()?.urlForSize(CoverSize.Large),
                    builder = {
                        crossfade(true)
                        scale(Scale.FILL)
                    }),
                contentScale = ContentScale.FillWidth,
                contentDescription = stringResource(id = R.string.ui_bookCover_altText)
            )
            // Display the book's summary info
            Column(
                modifier = Modifier
                    .padding(start = 2.dp, end = 2.dp, bottom = 1.dp, top = 1.dp)
            ) {
                // Display the title of the book
                work.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
                Spacer(modifier = Modifier.height(1.dp))
                // Display the name of the authors
                Text(
                    text = authors.value.toText(),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.testTag("worklist::author_name")
                )

                Spacer(modifier = Modifier.height(3.dp))
                // Display the list of subjects of the book
                if (displaySubjects) {
                    SubjectList(subjectNames = subjects.value)
                }

                Spacer(modifier = Modifier.height(3.dp))
                // Other stuff if needed
                footer?.let { footer(work) }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HorizontalBookView(
    work: Work,
    onClick: (work: Work) -> Unit,
    footer: (@Composable (work: Work) -> Unit)? = null,
    displaySubjects: Boolean
) {
    val covers = work.covers.collectAsState(initial = listOf())
    val authors = work.authors.collectAsState(initial = listOf())
    val subjects = work.subjects.collectAsState(initial = listOf())

    ElevatedCard(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        // On click, navigate to the book's details screen using its id
        onClick = { onClick(work) }
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Display the book's thumbnail
            if (covers.value.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(
                        data = covers.value.firstOrNull()?.urlForSize(CoverSize.Medium),
                        builder = {
                            crossfade(true)
                            scale(Scale.FILL)
                        }),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = stringResource(id = R.string.ui_bookCover_altText)
                )
            }

            // Display the book's summary info
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 5.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            ) {

                // Display the title of the book
                work.title?.let {
                    Text(text = it, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(1.dp))
                // Display the name of the authors
                Text(
                    text = authors.value.toText(),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.testTag("worklist::author_name")
                )


                Spacer(modifier = Modifier.height(3.dp))
                // Display the list of subjects of the book
                if (displaySubjects) {
                    SubjectList(subjectNames = subjects.value)
                }

                Spacer(modifier = Modifier.height(3.dp))
                // Other stuff if needed
                footer?.let { footer(work) }
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SubjectList(
    subjectNames: List<String>
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        subjectNames.map {
            SuggestionChip(
                colors = SuggestionChipDefaults.suggestionChipColors(
                    labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                onClick = { /*TODO*/ },
                label = { Text(text = it, style = MaterialTheme.typography.labelSmall) }
            )
        }
    }
}