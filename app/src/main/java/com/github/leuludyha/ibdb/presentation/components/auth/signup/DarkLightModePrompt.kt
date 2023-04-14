package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.runtime.Composable
import com.github.leuludyha.domain.model.authentication.AuthenticationContext

/**
 * Displays a component which prompts the user to use either light mode or dark mode
 */
object DarkLightModePrompt : SignUpPrompt {

    @Composable
    override fun Display(
        authContext: AuthenticationContext,
        onComplete: () -> Unit
    ) {

    }
}