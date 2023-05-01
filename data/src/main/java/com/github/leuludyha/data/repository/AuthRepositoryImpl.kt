package com.github.leuludyha.data.repository

import android.util.Log
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.AuthRepository
import com.github.leuludyha.domain.repository.OneTapSignInResponse
import com.github.leuludyha.domain.repository.SignInWithGoogleResponse
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Implementation of [AuthRepository] interface,
 * using googles BeginSignInRequest and Firebase Auth
 */
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
            Log.i("Auth", "Begin sign in...")
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Log.i("Auth", "Finished sign in...")
            Result.Success(signInResult)
        } catch (e: Exception) {
            try {
                Log.i("Auth", "Failed sign in, begin sign up")
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Log.i("Auth", "Finished sign up")
                Result.Success(signUpResult)
            } catch (e2: Exception) {
                Log.i("Auth", "Failed sign up")
                Result.Error(e2.message.toString())
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
