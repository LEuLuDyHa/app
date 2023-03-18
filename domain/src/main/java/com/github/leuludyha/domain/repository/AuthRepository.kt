package com.github.leuludyha.domain.repository

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.github.leuludyha.domain.model.Result

typealias OneTapSignInResponse = Result<BeginSignInResult>
typealias SignInWithGoogleResponse = Result<Boolean>

interface AuthRepository {

    suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse

}
