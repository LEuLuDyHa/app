package com.github.leuludyha.ibdb.presentation.screen.author_views

import android.graphics.Bitmap
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.navigation.testing.TestNavHostController
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.CoverSize
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.MockLibraryRepositoryImpl
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Mocks.authorGeorgeOrwell
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.GetAuthorRemotelyUseCase
import com.google.common.truth.Truth.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthorDetailsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun authorNameIsDisplayedOnSuccess() {
        composeTestRule.setContent {
            AuthorDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                authorId = authorRoaldDahl.id,
                viewModel = AuthorDetailsScreenViewModel(
                    _authContext = Mocks.authContext,
                    authorById = GetAuthorRemotelyUseCase(MockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithText(authorRoaldDahl.name!!).assertExists()
    }

    @Test
    fun errorIsDisplayedOnSuccessEmptyData() {
        composeTestRule.setContent {
            AuthorDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                authorId = authorRoaldDahl.id,
                viewModel = AuthorDetailsScreenViewModel(
                    _authContext = Mocks.authContext,
                    authorById = GetAuthorRemotelyUseCase(EmptyDataMockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithText("Error").assertExists()
    }

    @Test
    fun errorIsDisplayedOnErrorNonNullMessage() {
        composeTestRule.setContent {
            AuthorDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                authorId = authorGeorgeOrwell.id,
                viewModel = AuthorDetailsScreenViewModel(
                    _authContext = Mocks.authContext,
                    authorById = GetAuthorRemotelyUseCase(MockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithText("id not found").assertExists()
    }

    @Test
    fun notFoundIsDisplayedOnErrorNullMessage() {
        composeTestRule.setContent {
            AuthorDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                authorId = authorGeorgeOrwell.id,
                viewModel = AuthorDetailsScreenViewModel(
                    _authContext = Mocks.authContext,
                    authorById = GetAuthorRemotelyUseCase(NullErrorMessageLibraryRepository())
                )
            )
        }

        composeTestRule.onNodeWithText("Not Found").assertExists()
    }

    @Test
    fun loadingProgressIndicatorIsDisplayedWhenLoading() {
        composeTestRule.setContent {
            AuthorDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                authorId = authorGeorgeOrwell.id,
                viewModel = AuthorDetailsScreenViewModel(
                    _authContext = Mocks.authContext,
                    authorById = GetAuthorRemotelyUseCase(LoadingLibraryRepository())
                )
            )
        }

        composeTestRule.onNodeWithTag("progressIndicator").assertExists()
    }

    class LoadingLibraryRepository: LibraryRepository {
        override fun searchRemotely(query: String): Flow<PagingData<Work>> {
            TODO("Not yet implemented")
        }

        override fun getWorkRemotely(workId: String): Flow<Result<Work>> {
            TODO("Not yet implemented")
        }

        override fun getEditionRemotely(editionId: String): Flow<Result<Edition>> {
            TODO("Not yet implemented")
        }

        override fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>> {
            TODO("Not yet implemented")
        }

        override fun getAuthorRemotely(authorId: String): Flow<Result<Author>> =
            flowOf(Result.Loading())


        override suspend fun saveLocally(work: Work) {
            TODO("Not yet implemented")
        }

        override suspend fun saveLocally(author: Author) {
            TODO("Not yet implemented")
        }

        override suspend fun saveLocally(edition: Edition) {
            TODO("Not yet implemented")
        }

        override suspend fun saveLocally(workPref: WorkPreference) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(work: Work) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(author: Author) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(edition: Edition) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(workPref: WorkPreference) {
            TODO("Not yet implemented")
        }

        override fun getWorkLocally(workId: String): Flow<Work> {
            TODO("Not yet implemented")
        }

        override fun getAuthorLocally(authorId: String): Flow<Author> {
            TODO("Not yet implemented")
        }

        override fun getEditionLocally(editionId: String): Flow<Edition> {
            TODO("Not yet implemented")
        }

        override fun getEditionByISBNLocally(isbn: String): Flow<Edition> {
            TODO("Not yet implemented")
        }

        override fun getWorkPrefLocally(workId: String): Flow<WorkPreference> {
            TODO("Not yet implemented")
        }

        override fun getAllWorkPrefsLocally(): Flow<List<WorkPreference>> {
            TODO("Not yet implemented")
        }

        override fun getCoverBitmap(cover: Cover, coverSize: CoverSize): Flow<Bitmap> {
            TODO("Not yet implemented")
        }

    }

    class NullErrorMessageLibraryRepository: LibraryRepository {
        override fun searchRemotely(query: String): Flow<PagingData<Work>> {
            TODO("Not yet implemented")
        }

        override fun getWorkRemotely(workId: String): Flow<Result<Work>> {
            TODO("Not yet implemented")
        }

        override fun getEditionRemotely(editionId: String): Flow<Result<Edition>> {
            TODO("Not yet implemented")
        }

        override fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>> {
            TODO("Not yet implemented")
        }

        override fun getAuthorRemotely(authorId: String): Flow<Result<Author>> =
            flowOf(Result.Error(null))

        override suspend fun saveLocally(work: Work) {
            TODO("Not yet implemented")
        }

        override suspend fun saveLocally(author: Author) {
            TODO("Not yet implemented")
        }

        override suspend fun saveLocally(edition: Edition) {
            TODO("Not yet implemented")
        }

        override suspend fun saveLocally(workPref: WorkPreference) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(work: Work) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(author: Author) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(edition: Edition) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(workPref: WorkPreference) {
            TODO("Not yet implemented")
        }

        override fun getWorkLocally(workId: String): Flow<Work> {
            TODO("Not yet implemented")
        }

        override fun getAuthorLocally(authorId: String): Flow<Author> {
            TODO("Not yet implemented")
        }

        override fun getEditionLocally(editionId: String): Flow<Edition> {
            TODO("Not yet implemented")
        }

        override fun getEditionByISBNLocally(isbn: String): Flow<Edition> {
            TODO("Not yet implemented")
        }

        override fun getWorkPrefLocally(workId: String): Flow<WorkPreference> {
            TODO("Not yet implemented")
        }

        override fun getAllWorkPrefsLocally(): Flow<List<WorkPreference>> {
            TODO("Not yet implemented")
        }

        override fun getCoverBitmap(cover: Cover, coverSize: CoverSize): Flow<Bitmap> {
            TODO("Not yet implemented")
        }

    }

    class EmptyDataMockLibraryRepositoryImpl: LibraryRepository {
        override fun searchRemotely(query: String): Flow<PagingData<Work>> {
            TODO("Not yet implemented")
        }

        override fun getWorkRemotely(workId: String): Flow<Result<Work>> {
            TODO("Not yet implemented")
        }

        override fun getEditionRemotely(editionId: String): Flow<Result<Edition>> {
            TODO("Not yet implemented")
        }

        override fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>> {
            TODO("Not yet implemented")
        }

        override fun getAuthorRemotely(authorId: String): Flow<Result<Author>> =
            flowOf(Result.Success(null))

        override suspend fun saveLocally(work: Work) {
            TODO("Not yet implemented")
        }

        override suspend fun saveLocally(author: Author) {
            TODO("Not yet implemented")
        }

        override suspend fun saveLocally(edition: Edition) {
            TODO("Not yet implemented")
        }

        override suspend fun saveLocally(workPref: WorkPreference) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(work: Work) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(author: Author) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(edition: Edition) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLocally(workPref: WorkPreference) {
            TODO("Not yet implemented")
        }

        override fun getWorkLocally(workId: String): Flow<Work> {
            TODO("Not yet implemented")
        }

        override fun getAuthorLocally(authorId: String): Flow<Author> {
            TODO("Not yet implemented")
        }

        override fun getEditionLocally(editionId: String): Flow<Edition> {
            TODO("Not yet implemented")
        }

        override fun getEditionByISBNLocally(isbn: String): Flow<Edition> {
            TODO("Not yet implemented")
        }

        override fun getWorkPrefLocally(workId: String): Flow<WorkPreference> {
            TODO("Not yet implemented")
        }

        override fun getAllWorkPrefsLocally(): Flow<List<WorkPreference>> {
            TODO("Not yet implemented")
        }

        override fun getCoverBitmap(cover: Cover, coverSize: CoverSize): Flow<Bitmap> {
            TODO("Not yet implemented")
        }

    }
}