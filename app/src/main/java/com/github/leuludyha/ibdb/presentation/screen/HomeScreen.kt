package com.github.leuludyha.ibdb.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.github.leuludyha.ibdb.presentation.screen.search.BookSearchScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    outerPadding: PaddingValues,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setStatusBarColor(color = systemBarColor)
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(outerPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BookSearchScreen(navController = navController, padding = outerPadding)
        }
        Image(
            modifier = Modifier
                .padding(
                    end = 4.dp,
                )
                .width(120.dp),
            painter = rememberImagePainter(
                data = "https://covers.openlibrary.org/b/id/10521270-L.jpg",
                builder = {
                    crossfade(true)
                    scale(Scale.FILL)
                }),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}