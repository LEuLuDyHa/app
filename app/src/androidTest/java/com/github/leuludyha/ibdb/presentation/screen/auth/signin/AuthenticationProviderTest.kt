package com.github.leuludyha.ibdb.presentation.screen.auth.signin

import android.app.PendingIntent
import android.content.Intent
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.authentication.NearbyConnection
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.AuthRepository
import com.github.leuludyha.domain.repository.OneTapSignInResponse
import com.github.leuludyha.domain.repository.SignInWithGoogleResponse
import com.github.leuludyha.domain.useCase.auth.signin.FirebaseSignInUseCase
import com.github.leuludyha.domain.useCase.auth.signin.OneTapSignInUseCase
import com.github.leuludyha.domain.useCase.auth.signin.SignInUseCases
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.identity.zbp
import com.google.android.gms.common.api.internal.ApiKey
import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthenticationProviderTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    class MockSignInClient: SignInClient {
        override fun getApiKey(): ApiKey<zbp> {
            TODO("Not yet implemented")
        }

        override fun getSignInCredentialFromIntent(p0: Intent?): SignInCredential {
            TODO("Not yet implemented")
        }

        override fun beginSignIn(p0: BeginSignInRequest): Task<BeginSignInResult> {
            TODO("Not yet implemented")
        }

        override fun getPhoneNumberHintIntent(p0: GetPhoneNumberHintIntentRequest): Task<PendingIntent> {
            TODO("Not yet implemented")
        }

        override fun getSignInIntent(p0: GetSignInIntentRequest): Task<PendingIntent> {
            TODO("Not yet implemented")
        }

        override fun signOut(): Task<Void> {
            TODO("Not yet implemented")
        }

        override fun getPhoneNumberFromIntent(p0: Intent?): String {
            TODO("Not yet implemented")
        }

    }

    class MockErrorAuthRepository: AuthRepository {
        override val currentUser: FirebaseUser?
            get() = null

        override suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse =
            Result.Error("error")
            //Result.Success(BeginSignInResult(PendingIntent.readPendingIntentOrNullFromParcel(Parcel.obtain())!!))


        override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse =
            Result.Error("error")
    }

    val errorViewModel = AuthenticationProviderViewModel(
        MockSignInClient(),
        SignInUseCases(OneTapSignInUseCase(MockErrorAuthRepository()), FirebaseSignInUseCase(MockErrorAuthRepository())),
        AuthenticationContext(Mocks.mainUser, NearbyConnection.Empty)
    )

    class MockSuccessAuthRepository(
        val fireBaseSignInResult: Boolean
        ): AuthRepository {
        override val currentUser: FirebaseUser?
            get() = null

        override suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse =
            Result.Success(null)


        override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse =
            Result.Success(fireBaseSignInResult)
    }

    fun successViewModel(fireBaseSignInResult: Boolean) = AuthenticationProviderViewModel(
        MockSignInClient(),
        SignInUseCases(
            OneTapSignInUseCase(MockSuccessAuthRepository(fireBaseSignInResult)),
            FirebaseSignInUseCase(MockSuccessAuthRepository(fireBaseSignInResult))
        ),
        AuthenticationContext(Mocks.mainUser, NearbyConnection.Empty)
    )

    @Test
    fun authenticationProviderWithErrorViewModelDoesNotCrash() {
        composeTestRule.setContent {
            AuthenticationProvider(
                viewModel = errorViewModel,
                onSignedIn = { }
            ) { }
        }

        assertThat(true).isTrue()
    }

    @Test
    fun authenticationProviderWithSuccessFalseViewModelDoesNotCrash() {
        composeTestRule.setContent {
            AuthenticationProvider(
                viewModel = successViewModel(false),
                onSignedIn = { }
            ) { }
        }

        assertThat(true).isTrue()
    }

    @Test
    fun authenticationProviderWithSuccessTrueViewModelDoesNotCrash() {
        composeTestRule.setContent {
            AuthenticationProvider(
                viewModel = successViewModel(true),
                onSignedIn = { }
            ) { }
        }

        assertThat(true).isTrue()
    }
}