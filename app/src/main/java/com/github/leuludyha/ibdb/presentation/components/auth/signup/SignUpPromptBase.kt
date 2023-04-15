package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.ibdb.R

/**
 * Offers a base for most [SignUpPrompt] components
 */
abstract class SignUpPromptBase(
    /** Indicate whether this prompt can be skipped by the user or not */
    private val required: Boolean = false
) : SignUpPrompt {

    @Composable
    protected fun DefaultTitle(text: String) {
        Text(text = text, style = MaterialTheme.typography.titleLarge)
    }

    @Composable
    override fun Display(authContext: AuthenticationContext, onComplete: () -> Unit) {
        val isOptional = !required

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Title(authContext = authContext)
            }

            // Display the content of the prompt
            Content(
                authContext = authContext,
                onComplete = onComplete
            )

            // If the component is not required, display a "skip" button
            if (isOptional) {
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = onComplete, colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(text = stringResource(id = R.string.prompt_next_button))
                    }
                }
            }
        }
    }

    /**
     * The content of the prompt to display
     */
    @Composable
    abstract fun Content(
        authContext: AuthenticationContext,
        onComplete: () -> Unit
    )

    /**
     * The title of the prompt to display
     */
    @Composable
    abstract fun Title(
        authContext: AuthenticationContext,
    )

}