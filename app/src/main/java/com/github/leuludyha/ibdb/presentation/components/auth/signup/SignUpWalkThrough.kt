package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.github.leuludyha.domain.model.authentication.AuthenticationContext

/**
 * Swap between prompts on the sign up process
 * @param authContext The authentication context to complete
 * @param prompts The list of prompts to display, in the order in which they should be shown
 * @param onComplete The function to invoke when all prompts have been completed
 */
@Composable
fun SignUpWalkThrough(
    authContext: AuthenticationContext,
    prompts: List<SignUpPrompt>,
    onComplete: () -> Unit,
) {
    val (index, setIndex) = remember { mutableStateOf(0) }

    // If all prompts are completed, then call the "OnComplete" Function

    LaunchedEffect(index) {
        if (index >= prompts.size) {
            onComplete()
        }
    }

    if (index < prompts.size) {
        // Just go to the next prompt
        prompts[index].Display(authContext, onComplete = { setIndex(index + 1) })
    }
}