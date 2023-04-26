package com.github.leuludyha.ibdb.presentation.components.auth.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext

@Composable
fun LoadedAuthenticationContext(
    viewModel: LoadedAuthenticationContextViewModel = hiltViewModel(),
    onSignedIn: ((AuthenticationContext) -> Unit),
) {
    LaunchedEffect(Unit) {
        onSignedIn(viewModel.authContext)
    }
}