package com.github.leuludyha.ibdb.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// TODO REMOVE
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
            ElevatedButton(onClick = { navController.navigate(Screen.BookSearch.route) }) {
                Text("Search")
            }
            ElevatedButton(onClick = { navController.navigate(Screen.BarcodeScan.route) }) {
                Text("Barcode")
            }
        }
    }
}