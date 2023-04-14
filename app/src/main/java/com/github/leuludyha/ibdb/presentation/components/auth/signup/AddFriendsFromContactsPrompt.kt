package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.runtime.Composable
import com.github.leuludyha.domain.model.authentication.AuthenticationContext

/**
 * Display a component which prompts the user to
 * add friends who already downloaded the application and are
 * in their contacts
 */
object AddFriendsFromContactsPrompt : SignUpPrompt {

    @Composable
    override fun Display(
        authContext: AuthenticationContext,
        onComplete: () -> Unit
    ) {
        TODO("Not yet implemented")
    }
}