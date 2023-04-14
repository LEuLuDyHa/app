package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.runtime.Composable
import com.github.leuludyha.domain.model.authentication.AuthenticationContext

/**
 * Component which prompts the user to change their profile pictures.
 * They can use a custom one instead of the one linked to their google account
 */
object ChangeProfilePicturePrompt : SignUpPrompt {

    @Composable
    override fun Display(
        authContext: AuthenticationContext,
        onComplete: () -> Unit,
    ) {
        TODO("Not yet implemented")
    }
}