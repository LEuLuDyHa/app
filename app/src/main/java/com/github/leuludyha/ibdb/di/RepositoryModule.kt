package com.github.leuludyha.ibdb.di

import android.app.Application
import android.content.Context
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.data.repository.AuthRepositoryImpl
import com.github.leuludyha.data.repository.LibraryRepositoryImpl
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.repository.AuthRepository
import com.github.leuludyha.domain.repository.LibraryRepository
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideOpenLibraryRepository(libraryRemoteDataSource: LibraryRemoteDataSource) : LibraryRepository =
        LibraryRepositoryImpl(libraryRemoteDataSource)

    @Provides fun provideAuthRepository(
        @ApplicationContext context: Context,
        app: Application
    ): AuthRepository = AuthRepositoryImpl(

        oneTapClient = Identity.getSignInClient(context),

        signInRequest =
            BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(app.getString(R.string.web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .setAutoSelectEnabled(true)
            .build(),

        signUpRequest =
            BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(app.getString(R.string.web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()

    )

}