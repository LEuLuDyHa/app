package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.runtime.Composable
import com.github.leuludyha.domain.model.authentication.AuthenticationContext

/**
 * A component which prompt the user to enter some information
 * and complete the user's object with the given information
 */
interface SignUpPrompt {

    /**
     * The content to display when prompting the user
     * @param authContext The authentication context to complete with the information given
     * by the user
     * @param onComplete The method to invoke when the user has successfully completed the desired
     * information, in one way or another (A skip/ignore action from the user is considered
     * a completed prompt too)
     */
    @Composable
    fun Display(
        authContext: AuthenticationContext,
        onComplete: () -> Unit,
    )
}