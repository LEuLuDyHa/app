package com.github.leuludyha.ibdb.presentation.screen.auth.signin

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.navigation.BottomToolbar
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.github.leuludyha.domain.model.library.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
) {

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        viewModel.oneTapSignIn()
                    }
                ) {
                    Text(text = "Sign In") // TODO some string resource constant ?
                }
            }
        },
        bottomBar = { BottomToolbar(navController) }
    )

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val credentials = viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credentials.googleIdToken
                val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
                viewModel.firebaseSignIn(googleCredentials)
            } catch (it: ApiException) {
                print(it)
            }
        }
    }

    // OneTapSignIn
    when(val oneTapSignInResponse = viewModel.oneTapSignInResponse) {
        is Result.Loading -> CircularProgressIndicator()
        is Result.Success -> oneTapSignInResponse.data?.let {
            LaunchedEffect(it) {
                val intent = IntentSenderRequest.Builder(it.pendingIntent.intentSender).build()
                launcher.launch(intent)
            }
        }
        is Result.Error -> LaunchedEffect(Unit) {
            print(oneTapSignInResponse.message)
        }
    }

    // FirebaseSignIn
    when(val signInWithGoogleResponse = viewModel.firebaseSignInResponse) {
        is Result.Loading ->  CircularProgressIndicator()
        is Result.Success -> signInWithGoogleResponse.data?.let { signedIn ->
            LaunchedEffect(signedIn) {
                if (signedIn) {
                    // TODO("Show user profile")
                }
            }
        }
        is Result.Error -> LaunchedEffect(Unit) {
            print(signInWithGoogleResponse.message)
        }
    }

}
