package com.github.leuludyha.ibdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.ibdb.presentation.navigation.NavGraph
import com.github.leuludyha.ibdb.presentation.screen.auth.signin.AuthenticationProvider
import com.github.leuludyha.ibdb.presentation.screen.auth.signup.FirstTimeLogInCheck
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainContent()
        }
    }

    private var authContext: AuthenticationContext? = null

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MainContent()
    }

    @Composable
    fun MainContent() {
        navController = rememberNavController()

        // Tell if the authentication context is known or not
        val (isAuthContextPresent, setAuthContextPresent) = remember { mutableStateOf(false) }
        val (darkTheme, setDarkTheme) = remember { mutableStateOf(false) }

        // If the state change, refresh dark theme, sorry if it's a bit hacky
        LaunchedEffect(
            key1 = isAuthContextPresent,
            key2 = authContext?.principal?.preferences?.darkTheme?.value,
        ) {
            if (isAuthContextPresent) {
                // We can !! because authContext is present
                setDarkTheme(authContext!!.principal.preferences.darkTheme.value)
            }
        }

        IBDBTheme(darkTheme = if (isAuthContextPresent) darkTheme else isSystemInDarkTheme()) {

            AuthenticationProvider(
                onSignedIn = {
                    authContext = it
                    setAuthContextPresent(true)
                }
            ) {
                FirstTimeLogInCheck {
                    NavGraph(navController = navController)
                }
            }
        }
    }
}