package com.github.leuludyha.ibdb.di

import android.app.Application
import android.content.Context
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.data.repository.AuthRepositoryImpl
import com.github.leuludyha.data.repository.LibraryRepositoryImpl
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.repository.AuthRepository
import com.github.leuludyha.domain.repository.LibraryRepository
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Provides all the dependency injection related to the repository.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(
        libraryRemoteDataSource: LibraryRemoteDataSource,
        libraryLocalDataSource: LibraryLocalDataSource
    ) : LibraryRepository =
        LibraryRepositoryImpl(libraryRemoteDataSource, libraryLocalDataSource)

    @Provides
    fun oneTapClient(
        @ApplicationContext context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        app: Application
    ): AuthRepository = AuthRepositoryImpl(
        oneTapClient = oneTapClient(context),
        signInRequest = signInRequest(app, true),
        signUpRequest = signInRequest(app, false),
        auth = Firebase.auth,
        db = Firebase.firestore
    )

    private fun signInRequest(app: Application, filterAccounts: Boolean) =
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(app.getString(R.string.web_client_id))
                    .setFilterByAuthorizedAccounts(filterAccounts)
                    .build())
            .setAutoSelectEnabled(true)
            .build()

}
