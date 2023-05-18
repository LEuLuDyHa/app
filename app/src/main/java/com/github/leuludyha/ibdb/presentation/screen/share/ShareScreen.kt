package com.github.leuludyha.ibdb.presentation.screen.share

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.components.sharing.ShareWorkComponent
import com.github.leuludyha.ibdb.presentation.components.utils.Loading

@Composable
fun ShareScreen(
    navController: NavHostController,
    padding: PaddingValues,
    workId: String,
    viewModel: ShareScreenViewModel = hiltViewModel()
) {

    // Get json from local work
    SideEffect { viewModel.loadWorkJsonFrom(workId) }
    val workJsonResult by viewModel.workJson.collectAsState()

    workJsonResult?.let {
        ShareWorkComponent(
            padding = padding,
            workJson = it
        ) { navController.popBackStack() }
    } ?: Loading()

}