package com.github.leuludyha.ibdb.presentation.screen.share

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.components.sharing.ShareWorkComponent

@Composable
fun ShareScreen(
    navController: NavHostController,
    padding: PaddingValues,
    workId: String
) {

    ShareWorkComponent(
        padding = padding,
        workId = workId
    ) { navController.popBackStack() }
}