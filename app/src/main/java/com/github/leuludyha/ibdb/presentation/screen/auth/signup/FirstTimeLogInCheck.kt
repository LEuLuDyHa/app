package com.github.leuludyha.ibdb.presentation.screen.auth.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.ibdb.presentation.components.auth.signup.ChangeProfilePicturePrompt
import com.github.leuludyha.ibdb.presentation.components.auth.signup.ChangeUsernamePrompt
import com.github.leuludyha.ibdb.presentation.components.auth.signup.DarkLightModePrompt
import com.github.leuludyha.ibdb.presentation.components.auth.signup.SignUpWalkThrough
import com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends.AddFriendsFromContactsPrompt


/**
 * A screen which appears when the user signs in for the first time (Signs up to our app)
 * Prompts different info which are not given from the google account
 */
@Composable
fun FirstTimeLogInCheck(
    viewModel: FirstTimeLogInChecklViewModel = hiltViewModel(),
    onCheckPassed: (@Composable () -> Unit),
) {
    val context = LocalContext.current

    // If check is passed, display content
    if (viewModel.isWalkThroughCompleted(context)) {
        viewModel.updateWalkthroughPreferences(context)
        onCheckPassed()
    } else {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Otherwise, display the walk-through
            SignUpWalkThrough(
                authContext = viewModel.authContext,
                prompts = listOf(
                    ChangeUsernamePrompt,
                    ChangeProfilePicturePrompt,
                    DarkLightModePrompt,

                    // Lastly :
                    AddFriendsFromContactsPrompt,
                ),
                // On walk through complete, navigate to home screen
                onComplete = {
                    viewModel.rememberWalkThroughIsCompleted(context)
                    viewModel.persistWalkthroughOptions(context)
                }
            )
        }
    }
}