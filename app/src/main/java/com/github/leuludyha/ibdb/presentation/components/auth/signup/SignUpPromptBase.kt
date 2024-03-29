package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.github.leuludyha.domain.util.TestTag
import com.github.leuludyha.domain.util.testTag
import com.github.leuludyha.ibdb.R

/**
 * Offers a base for most [SignUpPrompt] components
 */
abstract class SignUpPromptBase(
    /** Indicate whether this prompt can be skipped by the user or not */
    private val required: Boolean = false
) : SignUpPrompt {

    object TestTags {
        val nextButton = TestTag("prompt-base-next-btn")
    }

    @Composable
    fun DefaultTitle(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }

    @Composable
    fun DefaultDisplay(content: @Composable () -> Unit, authContext: AuthenticationContext, onComplete: () -> Unit) {
        val isOptional = !required

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
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
            content()

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
                        ),
                        modifier = Modifier.testTag(TestTags.nextButton)
                    ) {
                        Text(text = stringResource(id = R.string.prompt_next_button))
                    }
                }
            }
        }
    }

    @Composable
    override fun Display(authContext: AuthenticationContext, onComplete: () -> Unit) {
        DefaultDisplay(
            content = {
                Content(
                    authContext = authContext,
                    onComplete = onComplete
                )
            },
            authContext = authContext,
            onComplete = onComplete
        )
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