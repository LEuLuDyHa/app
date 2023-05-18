package com.github.leuludyha.ibdb.presentation.screen.auth.signin

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.data.nearby_connection.NearbyConnectionImpl
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.OneTapSignInResponse
import com.github.leuludyha.domain.repository.SignInWithGoogleResponse
import com.github.leuludyha.domain.useCase.auth.signin.SignInUseCases
import com.github.leuludyha.ibdb.util.Constant
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the SignIn screen,
 * gives the UI access to the responses current values
 */
@HiltViewModel
class AuthenticationProviderViewModel @Inject constructor(
    val oneTapClient: SignInClient, // TODO AR
    private val signInUseCases: SignInUseCases,
    val authContext: AuthenticationContext
) : ViewModel() {

    // variables keep track of the state of the request and can be observed by the UI
    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse?>(null)
        private set

    //    var firebaseSignInResponse by mutableStateOf<SignInWithGoogleResponse>(Result.Loading())
    var firebaseSignInResponse by mutableStateOf<SignInWithGoogleResponse?>(null)
        private set

    fun oneTapSignIn() = viewModelScope.launch {
        oneTapSignInResponse = Result.Loading()
        oneTapSignInResponse = signInUseCases.oneTapSignInUseCase.invoke()
    }

    fun firebaseSignIn(googleCredential: AuthCredential) = viewModelScope.launch {
        firebaseSignInResponse = Result.Loading()
        firebaseSignInResponse = signInUseCases.firebaseSignInUseCase.invoke(googleCredential)
    }

    /**
     * This method will update the fields from authentication context that can be updated from firebase.
     */
    fun loadAuthenticationContextFromFirebase(context: Context) {
        authContext.principal.userId = Firebase.auth.currentUser?.uid!!
        //The username may be updated from other places with a higher priority
        //I am aware doing this is an awful practice, but I am also out of ideas to make it cleaner
        //This below is a patch, and hopefully someone comes up with a better way.
        if (authContext.principal.username == Constant.USER_NOT_FOUND) {
            authContext.principal.username =
                Firebase.auth.currentUser?.displayName ?: authContext.principal.username
        }
        authContext.principal.profilePictureUrl = Firebase.auth.currentUser?.photoUrl.toString()
        authContext.principal.phoneNumber = Firebase.auth.currentUser?.phoneNumber

        // Initialize the nearby connection object
        initializeConnection(context)
    }

    /**
     * Will load the current authentication context's username, ID, phone number and profile picture url
     * from persistent memory using SharedPreferences.
     */
    fun loadAuthenticationContextFromLocalMemory(context: Context) {
        val sp = context.getSharedPreferences(
            Constant.AUTHENTICATION_CONTEXT_STORED,
            Context.MODE_PRIVATE
        )
        authContext.principal.userId =
            sp.getString(Constant.AUTHENTICATION_CONTEXT_STORED_USERID, Constant.USER_NOT_FOUND)!!
        authContext.principal.username =
            sp.getString(Constant.AUTHENTICATION_CONTEXT_STORED_USERNAME, Constant.USER_NOT_FOUND)!!
        authContext.principal.profilePictureUrl = sp.getString(
            Constant.AUTHENTICATION_CONTEXT_STORED_PROFILE_PICTURE_URL,
            Constant.USER_NOT_FOUND
        )!!
        authContext.principal.phoneNumber = sp.getString(
            Constant.AUTHENTICATION_CONTEXT_STORED_PHONE_NUMBER,
            Constant.USER_NOT_FOUND
        )!!

        // Initialize the nearby connection object
        initializeConnection(context)
    }

    private fun initializeConnection(context: Context) {
        authContext.nearbyConnection = NearbyConnectionImpl(
            context, authContext.principal.username,
        )
    }

    /**
     * Will write the current authentication context's username, ID, phone number and profile picture url
     * to persistent memory using SharedPreferences.
     */
    fun writeAuthenticationContextToPersistentMemory(context: Context) {
        val sp = context.getSharedPreferences(
            Constant.AUTHENTICATION_CONTEXT_STORED,
            Context.MODE_PRIVATE
        )

        val spEditor = sp.edit()
        spEditor.putString(
            Constant.AUTHENTICATION_CONTEXT_STORED_USERID,
            authContext.principal.userId
        )
        spEditor.putString(
            Constant.AUTHENTICATION_CONTEXT_STORED_USERNAME,
            authContext.principal.username
        )
        spEditor.putString(
            Constant.AUTHENTICATION_CONTEXT_STORED_PROFILE_PICTURE_URL,
            authContext.principal.profilePictureUrl
        )
        spEditor.putString(
            Constant.AUTHENTICATION_CONTEXT_STORED_PHONE_NUMBER,
            authContext.principal.phoneNumber
        )
        spEditor.apply()
    }
}
