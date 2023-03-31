package com.github.leuludyha.ibdb.presentation.components.book_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.github.leuludyha.domain.model.library.CoverSize
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.util.toText
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.reading_list.controls.ReadingStateControl

@Composable
fun FullBookView(
    navController: NavHostController,
    work: Work
) {
    val covers = work.covers.collectAsState(initial = listOf())
    val authors = work.authors.collectAsState(initial = listOf())
    val subjects = work.subjects.collectAsState(initial = listOf())

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = work.title.orEmpty(), style = MaterialTheme.typography.titleLarge)
        Text(text = authors.value.toText(), style = MaterialTheme.typography.titleSmall)
        ReadingStateControl(work = work)
        Image(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            painter = rememberImagePainter(
                data = covers.value.firstOrNull()?.urlForSize(CoverSize.Large),
                builder = {
                    crossfade(true)
                    scale(Scale.FILL)
                }),
            contentScale = ContentScale.FillWidth,
            contentDescription = stringResource(id = R.string.ui_bookCover_altText)
        )
    }
}