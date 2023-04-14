package com.github.leuludyha.ibdb.presentation.screen.auth.signup

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.components.auth.signup.ChangeProfilePicturePrompt
import com.github.leuludyha.ibdb.presentation.components.auth.signup.ChangeUsernamePrompt
import com.github.leuludyha.ibdb.presentation.components.auth.signup.SignUpWalkThrough
import com.github.leuludyha.ibdb.presentation.navigation.Screen


/**
 * A screen which appears when the user signs in for the first time (Signs up to our app)
 * Prompts different info which are not given from the google account
 */
@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpScreenViewModel = hiltViewModel()
) {
    // If the walk through is already completed, go to home screen
    if (viewModel.isWalkThroughCompleted()) {
        navController.navigate(Screen.Home.route)
        return
    }

    // Otherwise, display the walk-through
    SignUpWalkThrough(
        authContext = viewModel.authContext,
        prompts = listOf(
            ChangeUsernamePrompt,
            ChangeProfilePicturePrompt,

            ),
        // On walk through complete, navigate to home screen
        onComplete = {
            viewModel.rememberWalkThroughIsCompleted()
            viewModel.persistUserState()
        }
    )
}