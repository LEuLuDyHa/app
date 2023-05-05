package com.github.leuludyha.ibdb.presentation.screen.auth.signin

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Parcel
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.AuthRepository
import com.github.leuludyha.domain.repository.OneTapSignInResponse
import com.github.leuludyha.domain.repository.SignInWithGoogleResponse
import com.github.leuludyha.domain.useCase.auth.signin.FirebaseSignInUseCase
import com.github.leuludyha.domain.useCase.auth.signin.OneTapSignInUseCase
import com.github.leuludyha.domain.useCase.auth.signin.SignInUseCases
import com.github.leuludyha.ibdb.util.Constant
import com.github.leuludyha.ibdb.util.Constant.AUTHENTICATION_CONTEXT_STORED_PHONE_NUMBER
import com.github.leuludyha.ibdb.util.Constant.AUTHENTICATION_CONTEXT_STORED_PROFILE_PICTURE_URL
import com.github.leuludyha.ibdb.util.Constant.AUTHENTICATION_CONTEXT_STORED_USERID
import com.github.leuludyha.ibdb.util.Constant.AUTHENTICATION_CONTEXT_STORED_USERNAME
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
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthenticationProviderViewModelTest {

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
        AuthenticationContext(Mocks.mainUser)
    )

    class MockSuccessAuthRepository(
        val fireBaseSignInResult: Boolean
        ): AuthRepository {
        override val currentUser: FirebaseUser?
            get() = null

        override suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse =
            Result.Success(BeginSignInResult(PendingIntent.readPendingIntentOrNullFromParcel(Parcel.obtain())!!))


        override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse =
            Result.Success(fireBaseSignInResult)
    }

    fun successViewModel(fireBaseSignInResult: Boolean) = AuthenticationProviderViewModel(
        MockSignInClient(),
        SignInUseCases(
            OneTapSignInUseCase(MockSuccessAuthRepository(fireBaseSignInResult)),
            FirebaseSignInUseCase(MockSuccessAuthRepository(fireBaseSignInResult))
        ),
        AuthenticationContext(Mocks.mainUser)
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun oneTapSignInResponseIsErrorOnError() = runTest {
        val viewModel = errorViewModel
        viewModel.oneTapSignIn().join()
        assertThat(viewModel.oneTapSignInResponse).isInstanceOf(Result.Error::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun firebaseSignInResponseIsSuccessOnSuccessTrue() = runTest {
        val viewModel = successViewModel(true)
        viewModel.firebaseSignIn(GoogleAuthProvider.getCredential("googleIdToken", null)).join()
        assertThat(viewModel.firebaseSignInResponse?.data).isTrue()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun firebaseSignInResponseIsSuccessOnSuccessFalse() = runTest {
        val viewModel = successViewModel(false)
        viewModel.firebaseSignIn(GoogleAuthProvider.getCredential("googleIdToken", null)).join()
        assertThat(viewModel.firebaseSignInResponse?.data).isFalse()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun firebaseSignInResponseIsErrorOnError() = runTest {
        val viewModel = errorViewModel
        viewModel.firebaseSignIn(GoogleAuthProvider.getCredential("googleIdToken", null)).join()
        assertThat(viewModel.firebaseSignInResponse).isInstanceOf(Result.Error::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadAuthenticationContextFromFirebaseThrowsNullPointerExceptionWhenNoUserFound() {
        assertThrows(java.lang.NullPointerException::class.java) { runTest {
            val viewModel = errorViewModel
            viewModel.loadAuthenticationContextFromFirebase()
        }}
    }

    @Test
    fun loadAuthenticationContextFromLocalMemoryCorrectlyLoadsDataFromContext() {
        val viewModel = errorViewModel
        val context = InstrumentationRegistry.getInstrumentation().context
        context.getSharedPreferences(
            Constant.AUTHENTICATION_CONTEXT_STORED,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(AUTHENTICATION_CONTEXT_STORED_USERID, "1")
            .putString(AUTHENTICATION_CONTEXT_STORED_USERNAME, "2")
            .putString(AUTHENTICATION_CONTEXT_STORED_PROFILE_PICTURE_URL, "3")
            .putString(AUTHENTICATION_CONTEXT_STORED_PHONE_NUMBER, "4")
            .commit()

        viewModel.loadAuthenticationContextFromLocalMemory(context)
        assertThat(viewModel.authContext.principal.userId).isEqualTo("1")
        assertThat(viewModel.authContext.principal.username).isEqualTo("2")
        assertThat(viewModel.authContext.principal.profilePictureUrl).isEqualTo("3")
        assertThat(viewModel.authContext.principal.phoneNumber).isEqualTo("4")
    }

    @Test
    fun loadAuthenticationContextFromLocalMemoryLoadsWrongDataFromContextOnNonStoredData() {
        val viewModel = errorViewModel
        val context = InstrumentationRegistry.getInstrumentation().context

        viewModel.loadAuthenticationContextFromLocalMemory(context)
        assertThat(viewModel.authContext.principal.userId).isEqualTo(Constant.USER_NOT_FOUND)
        assertThat(viewModel.authContext.principal.username).isEqualTo(Constant.USER_NOT_FOUND)
        assertThat(viewModel.authContext.principal.profilePictureUrl).isEqualTo(Constant.USER_NOT_FOUND)
        assertThat(viewModel.authContext.principal.phoneNumber).isEqualTo(Constant.USER_NOT_FOUND)
    }

    @Test
    fun writeAuthenticationContextToPersistentMemoryCorrectlyWrites() {
        val viewModel = errorViewModel
        val context = InstrumentationRegistry.getInstrumentation().context
        viewModel.writeAuthenticationContextToPersistentMemory(context)

        val sp = context.getSharedPreferences(
            Constant.AUTHENTICATION_CONTEXT_STORED,
            Context.MODE_PRIVATE
        )

        assertThat(sp.getString(AUTHENTICATION_CONTEXT_STORED_USERID, null)).isEqualTo(viewModel.authContext.principal.userId)
        assertThat(sp.getString(AUTHENTICATION_CONTEXT_STORED_USERNAME, null)).isEqualTo(viewModel.authContext.principal.username)
        assertThat(sp.getString(AUTHENTICATION_CONTEXT_STORED_PROFILE_PICTURE_URL, null)).isEqualTo(viewModel.authContext.principal.profilePictureUrl)
        assertThat(sp.getString(AUTHENTICATION_CONTEXT_STORED_PHONE_NUMBER, null)).isEqualTo(viewModel.authContext.principal.phoneNumber)
    }

}