package com.github.leuludyha.ibdb.di

import com.github.leuludyha.domain.model.library.recommendation.RecommenderSystem
import com.github.leuludyha.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Provides all the dependency injection related to the recommender module.
 */
@Module
@InstallIn(SingletonComponent::class)
object RecommenderModule {

    @Provides
    fun provideRecommender(
        userRepository: UserRepository
    ): RecommenderSystem = RecommenderSystem(userRepository)
}