package com.github.leuludyha.ibdb.presentation.screen.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.ui.navigation.BottomToolbar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val systemUiController = rememberSystemUiController()

    SideEffect {
        // TODO
    }

    Scaffold(
        bottomBar = { BottomToolbar() },
    ) { it }
}