package com.github.leuludyha.ibdb.presentation.screen.auth.signin

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.auth.signin.LoadedAuthenticationContext
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

/**
 * SignInScreen allows user to sign in with a button for Google One Tap
 *
 * Once the user is signed in, displays the content of [signedInContent]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationProvider(
    viewModel: SignInViewModel = hiltViewModel(),
    onSignedIn: (AuthenticationContext) -> Unit,
    signedInContent: (@Composable () -> Unit),
) {
    val (signedIn, setSignedIn) = remember { mutableStateOf(false) }

    if (signedIn) {
        // Notify parent on signed in
        LoadedAuthenticationContext { onSignedIn(it) }
        signedInContent()
    } else {
        Scaffold(
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.oneTapSignIn()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.sign_in_label))
                    }
                }
            },
        )

        // will invoke firebaseSignIn() when oneTapSignInResponse
        // once we have a response from OneTapSignIn
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credentials =
                        viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                    val googleIdToken = credentials.googleIdToken
                    val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
                    viewModel.firebaseSignIn(googleCredentials)
                } catch (it: ApiException) {
                    print(it)
                }
            }
        }

        // OneTapSignIn
        when (val oneTapSignInResponse = viewModel.oneTapSignInResponse) {
            null -> {} // not started yet
            is Result.Loading -> {} // Google pane already has an indicator // CircularProgressIndicator()
            is Result.Success -> oneTapSignInResponse.data?.let {
                Log.i("one tap sign in response success", oneTapSignInResponse.data.toString())
                LaunchedEffect(it) {
                    val intent = IntentSenderRequest.Builder(it.pendingIntent.intentSender).build()
                    launcher.launch(intent)
                }
            }
            is Result.Error -> LaunchedEffect(Unit) {
                Log.i("Auth", oneTapSignInResponse.message.toString())
            }
        }

        // FirebaseSignIn
        when (val signInWithGoogleResponse = viewModel.firebaseSignInResponse) {
            null -> {} // not started yet
            is Result.Loading ->
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(50.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    CircularProgressIndicator()
                }
            is Result.Success -> signInWithGoogleResponse.data?.let { resSignedIn ->
                LaunchedEffect(resSignedIn) {
                    if (resSignedIn) {
                        setSignedIn(true)
                    }
                }
            }
            is Result.Error -> LaunchedEffect(Unit) {
                Log.i("Auth", signInWithGoogleResponse.message.toString())
            }
        }

    }
}
