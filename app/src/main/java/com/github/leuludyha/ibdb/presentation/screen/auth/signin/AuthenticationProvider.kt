package com.github.leuludyha.ibdb.presentation.screen.auth.signin

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Result
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


/**
 * Tries logging the user in through Google One Tap on launch.
 * Once the user is signed in, calls [onSignedIn] and displays the content of [signedInContent].
 */
@Composable
fun AuthenticationProvider(
    viewModel: AuthenticationProviderViewModel = hiltViewModel(),
    onSignedIn: (AuthenticationContext) -> Unit,
    signedInContent: (@Composable () -> Unit),
) {
    val (signedIn, setSignedIn) = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(context) {
        if (checkNetworkAvailable(context)) {
            viewModel.oneTapSignIn()
            loadAuthenticationContextFromFirebase(viewModel.authContext)
        } else {
            setSignedIn(true)
        }
    }

    if (signedIn) {
        // Notify parent on signed in
        onSignedIn(viewModel.authContext)
        signedInContent()
    } else {

        //TODO: Maybe add some decoration here, instead of an empty white screen on the background

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

/**
 * This method will update the fields from authentication context that can be update from firebase.
 */
private fun loadAuthenticationContextFromFirebase(authContext: AuthenticationContext) {
    authContext.principal.userId = Firebase.auth.currentUser?.uid!!
    authContext.principal.username =
        Firebase.auth.currentUser?.displayName ?: authContext.principal.username
    authContext.principal.profilePictureUrl = Firebase.auth.currentUser?.photoUrl.toString()
    authContext.principal.phoneNumber = Firebase.auth.currentUser?.phoneNumber
}

/**
 * This is implemented following the recommendations from [here](https://stackoverflow.com/questions/56709604/check-for-internet-connectivity-on-api29).
 *
 * @return true if a network is available. Both wifi and mobile are acceptable.
 */
private fun checkNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkAvailability =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    // TODO: check https://stackoverflow.com/questions/25678216/android-internet-connectivity-change-listener
//    connectivityManager.let {
//        it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
//            override fun onAvailable(network: Network) {
//                //take action when network connection is gained
//            }
//            override fun onLost(network: Network?) {
//                //take action when network connection is lost
//            }
//        })
//    }
    return networkAvailability != null
            && networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            && networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}
