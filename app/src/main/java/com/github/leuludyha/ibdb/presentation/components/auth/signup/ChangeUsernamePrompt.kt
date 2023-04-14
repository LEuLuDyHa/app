package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.ibdb.R

/**
 * Displays a component which prompts the user to change their username instead of the default
 * one they have linked to their google account
 */
object ChangeUsernamePrompt : SignUpPromptBase(
    required = false
) {

    @Composable
    override fun Content(authContext: AuthenticationContext, onComplete: () -> Unit) {

        TextField(
            value = authContext.principal.username,
            onValueChange = { authContext.principal.username = it }
        )
    }

    @Composable
    override fun Title(authContext: AuthenticationContext) {
        Text(
            text = (
                    stringResource(id = R.string.prompt_username_title_begin) +
                            authContext.principal.username +
                            stringResource(id = R.string.prompt_username_title_end)
                    )
        )
    }

}