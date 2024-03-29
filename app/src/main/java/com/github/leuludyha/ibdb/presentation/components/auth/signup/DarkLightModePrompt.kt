package com.github.leuludyha.ibdb.presentation.components.auth.signup

import android.annotation.TargetApi
import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.util.TestTag
import com.github.leuludyha.domain.util.testTag
import com.github.leuludyha.ibdb.R

private const val DynamicThemeApiLevel = 31

/**
 * Displays a component which prompts the user to use either light mode or dark mode
 */
object DarkLightModePrompt : SignUpPrompt {

    object TestTags {
        val toggleThemeBtn = TestTag("toggle-theme-btn")
    }

    var isDynamicCompatible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            && Build.VERSION.SDK_INT >= DynamicThemeApiLevel

    @Composable
    @TargetApi(DynamicThemeApiLevel)
    override fun Display(
        authContext: AuthenticationContext,
        onComplete: () -> Unit
    ) {
        val context = LocalContext.current
        // Whether the theme should be dark or light
        val (darkTheme, setDarkTheme) = remember { authContext.principal.userPreferences.darkTheme }

        // Collects the size of the background when it is mounted on the view
        val (colSize, setColSize) = remember { mutableStateOf(Pair(0f, 0f)) }

        // Blending factor for background color gradient, animated on theme swap
        val blendingFactor: Float by animateFloatAsState(if (darkTheme) 2f else 0f)

        // Fetch the dynamic dark and light themes
        val darkBackground =
            if (isDynamicCompatible) dynamicDarkColorScheme(context).background else Color.DarkGray
        val lightBackground =
            if (isDynamicCompatible) dynamicLightColorScheme(context).background else Color.LightGray

        // Stay at 0 in first half of animation, then go to 100%
        val startFactor = if (blendingFactor <= 1f) 0f else blendingFactor - 1f
        // Go to 100% in first half of animation, then stay at 100%
        val endFactor = if (blendingFactor >= 1f) 1f else blendingFactor

        Column(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    // Save background size when mounted
                    setColSize(Pair(it.size.width.toFloat(), it.size.height.toFloat()))
                }
                .background(
                    Brush.linearGradient(
                        colors = listOf(darkBackground, lightBackground),
                        start = Offset(
                            startFactor * colSize.first,
                            startFactor * colSize.second
                        ),
                        end = Offset(
                            // +1 is to remove glitches of background
                            endFactor * colSize.first + 1f,
                            endFactor * colSize.second + 1f,
                        ),
                        tileMode = TileMode.Clamp
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                // Display the title
                Text(
                    text = stringResource(id = R.string.dark_mode_prompt_title),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Button to swap between light and dark theme
            Button(
                onClick = { setDarkTheme(!darkTheme) },
                modifier = Modifier.testTag(TestTags.toggleThemeBtn)
            ) {
                Text(text = stringResource(id = R.string.dark_mode_prompt_button))
            }
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Button to skip to next
                Button(
                    onClick = { onComplete() }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.testTag(SignUpPromptBase.TestTags.nextButton)
                ) {
                    Text(text = stringResource(id = R.string.prompt_next_button))
                }
            }
        }
    }
}