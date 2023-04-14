package com.github.leuludyha.ibdb.presentation.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.ibdb.R

/**
 * Filters unauthenticated principal and redirect them to authentication
 * @param authContext The current authentication context of the app
 * @param fallback The component displayed in case the user is not logged in
 * @param content Displayed only if the authentication context contains an
 * authenticated principal
 */
@Composable
fun DisplayIfAuthenticated(
    authContext: AuthenticationContext,
    fallback: (@Composable () -> Unit)? = null,
    content: @Composable (authenticatedPrincipal: User) -> Unit
) {

    if (authContext.principal != null) {
        content(authContext.principal)
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            fallback?.let { fallback() }
                ?: run { Text(text = stringResource(id = R.string.ui_authentication_notauthenticated)) }
        }
    }
}