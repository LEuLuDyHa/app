package com.github.leuludyha.ibdb.presentation.screen.auth.signin

import android.app.Activity
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.navigation.BottomToolbar
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel() // TODO take padding ?
) {

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
                    Text(text = stringResource(R.string.Sign_In_button))
                }
            }
        },
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
        is Result.Loading -> {} // TODO CircularProgressIndicator()
        is Result.Success -> oneTapSignInResponse.data?.let {
            Log.i("one tap sign in response success", oneTapSignInResponse.data.toString())
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
        is Result.Loading -> {} // TODO CircularProgressIndicator()
        is Result.Success -> signInWithGoogleResponse.data?.let { signedIn ->
            LaunchedEffect(signedIn) {
                if (signedIn) {
                    navController.navigate(
                        route = Screen.Profile.route
                    )
                }
            }
        }
        is Result.Error -> LaunchedEffect(Unit) {
            print(signInWithGoogleResponse.message)
        }
    }

}
