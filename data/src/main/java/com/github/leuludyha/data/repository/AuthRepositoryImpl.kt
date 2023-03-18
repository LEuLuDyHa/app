package com.github.leuludyha.data.repository

import com.github.leuludyha.domain.repository.AuthRepository
import com.github.leuludyha.domain.repository.OneTapSignInResponse
import com.github.leuludyha.domain.repository.SignInWithGoogleResponse
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.github.leuludyha.domain.model.library.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// TODO ? data sources for :
//      - OneTap: SignInClient, BeginSignInRequest, BeginSignUpRequest
//      - Firebase: FirebaseAuth, FirebaseFirestore
//  but cannot abstract over the OneTapSignInResponse and AuthCredential types

class AuthRepositoryImpl(
    private var oneTapClient: SignInClient,
    private var signInRequest: BeginSignInRequest,
    private var signUpRequest: BeginSignInRequest,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
): AuthRepository {

    override val currentUser = auth.currentUser

    override suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Result.Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Result.Success(signUpResult)
            } catch (e: Exception) {
                Result.Error(e.message.toString())
            }
        }
    }

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            val authResult = auth.signInWithCredential(googleCredential).await()
            if (authResult.additionalUserInfo?.isNewUser == true) {
                auth.currentUser?.apply {
                    // TODO("add user to firestore db")
                }
            }
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }

}
