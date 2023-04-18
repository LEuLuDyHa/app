package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.util.TestTag
import com.github.leuludyha.domain.util.testTag
import com.github.leuludyha.ibdb.R

/**
 * Displays a component which prompts the user to change their username instead of the default
 * one they have linked to their google account
 */
object ChangeUsernamePrompt : SignUpPromptBase(
    required = false
) {

    object TestTags {
        val usernameField = TestTag("change-username-field")
    }

    @Composable
    override fun Content(authContext: AuthenticationContext, onComplete: () -> Unit) {

        val (username, setUsername) = remember {
            mutableStateOf(authContext.principal.username)
        }

        // Update profile var on state change
        LaunchedEffect(username) {
            authContext.principal.username = username
        }

        TextField(
            modifier = Modifier.testTag(TestTags.usernameField),
            value = username,
            onValueChange = { setUsername(it) }
        )
    }

    @Composable
    override fun Title(authContext: AuthenticationContext) {
        DefaultTitle(
            text =
            stringResource(id = R.string.prompt_username_title_begin) +
                    authContext.principal.username +
                    stringResource(id = R.string.prompt_username_title_end)
        )
    }

}