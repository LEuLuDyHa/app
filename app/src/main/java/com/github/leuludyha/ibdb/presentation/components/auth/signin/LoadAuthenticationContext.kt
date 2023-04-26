package com.github.leuludyha.ibdb.presentation.components.auth.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext

/**
 * This is an unfortunate patch to be able to call the authenticationContext from inside
 * the authentication provider. It cannot be directly injected earlier since it must be initialized
 * using Firebase, which is not available until after authentication provider has executed.
 */
@Composable
fun LoadAuthenticationContext(
    viewModel: LoadAuthenticationContextViewModel = hiltViewModel(),
    onSignedIn: ((AuthenticationContext) -> Unit),
) {
    LaunchedEffect(Unit) {
        onSignedIn(viewModel.authContext)
    }
}