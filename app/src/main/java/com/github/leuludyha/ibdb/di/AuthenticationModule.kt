package com.github.leuludyha.ibdb.di

import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Mocks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    // TODO Change : Link this in some way to the google authentication process
    @Provides
    fun provideAuthenticationContext(): AuthenticationContext = AuthenticationContext(Mocks.user)
}