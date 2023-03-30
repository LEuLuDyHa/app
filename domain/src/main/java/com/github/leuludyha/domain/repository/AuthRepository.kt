package com.github.leuludyha.domain.repository

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.github.leuludyha.domain.model.library.Result

typealias OneTapSignInResponse = Result<BeginSignInResult>
typealias SignInWithGoogleResponse = Result<Boolean>

/**
 * Interface for the AuthRepository,
 */
interface AuthRepository {

    val currentUser: FirebaseUser?

    // using the device's Google account to sign in
    suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse

    // using the obtained google credential to sign into firebase
    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse

}
