package com.github.leuludyha.domain.useCases

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.domain.MockLocalFailureRemoteSuccessLibraryRepositoryImpl
import com.github.leuludyha.domain.MockLocalRemoteFailureLibraryRepositoryImpl
import com.github.leuludyha.domain.MockLocalSuccessLibraryRepositoryImpl
import com.github.leuludyha.domain.MockTrueNetworkProvider
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.useCase.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MixedUseCasesTest {
    lateinit var context: Context
    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkUseCaseGivesCorrectResultOnLocalSuccess() = runTest {
        val repo = MockLocalSuccessLibraryRepositoryImpl()
        val local = GetWorkLocallyUseCase(repo)
        val remote = GetWorkRemotelyUseCase(repo, MockTrueNetworkProvider)

        val data = GetWorkUseCase(local, remote)(workMrFox.id, context).data
        assertThat(data).isEqualTo(workMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkUseCaseGivesCorrectResultOnLocalFailureRemoteSuccess() = runTest {
        val repo = MockLocalFailureRemoteSuccessLibraryRepositoryImpl()
        val local = GetWorkLocallyUseCase(repo)
        val remote = GetWorkRemotelyUseCase(repo, MockTrueNetworkProvider)

        val data = GetWorkUseCase(local, remote)(workMrFox.id, context).data
        assertThat(data).isEqualTo(workMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkUseCaseGivesCorrectResultOnLocalRemoteFailureSuccess() = runTest {
        val repo = MockLocalRemoteFailureLibraryRepositoryImpl()
        val local = GetWorkLocallyUseCase(repo)
        val remote = GetWorkRemotelyUseCase(repo, MockTrueNetworkProvider)

        val data = GetWorkUseCase(local, remote)(workMrFox.id, context)
        assertThat(data).isInstanceOf(Result.Error::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorUseCaseGivesCorrectResultOnLocalSuccess() = runTest {
        val repo = MockLocalSuccessLibraryRepositoryImpl()
        val local = GetAuthorLocallyUseCase(repo)
        val remote = GetAuthorRemotelyUseCase(repo, MockTrueNetworkProvider)

        val data = GetAuthorUseCase(local, remote)("id", context).data
        assertThat(data).isEqualTo(authorRoaldDahl)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorUseCaseGivesCorrectResultOnLocalFailureRemoteSuccess() = runTest {
        val repo = MockLocalFailureRemoteSuccessLibraryRepositoryImpl()
        val local = GetAuthorLocallyUseCase(repo)
        val remote = GetAuthorRemotelyUseCase(repo, MockTrueNetworkProvider)

        val data = GetAuthorUseCase(local, remote)("id", context).data
        assertThat(data).isEqualTo(authorRoaldDahl)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorUseCaseGivesCorrectResultOnLocalRemoteFailureSuccess() = runTest {
        val repo = MockLocalRemoteFailureLibraryRepositoryImpl()
        val local = GetAuthorLocallyUseCase(repo)
        val remote = GetAuthorRemotelyUseCase(repo, MockTrueNetworkProvider)

        val data = GetAuthorUseCase(local, remote)(workMrFox.id, context)
        assertThat(data).isInstanceOf(Result.Error::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionUseCaseGivesCorrectResultOnLocalSuccess() = runTest {
        val repo = MockLocalSuccessLibraryRepositoryImpl()
        val local = GetEditionLocallyUseCase(repo)
        val remote = GetEditionRemotelyUseCase(repo, MockTrueNetworkProvider)

        val data = GetEditionUseCase(local, remote)(workMrFox.id, context).data
        assertThat(data).isEqualTo(editionMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionUseCaseGivesCorrectResultOnLocalFailureRemoteSuccess() = runTest {
        val repo = MockLocalFailureRemoteSuccessLibraryRepositoryImpl()
        val local = GetEditionLocallyUseCase(repo)
        val remote = GetEditionRemotelyUseCase(repo, MockTrueNetworkProvider)

        val data = GetEditionUseCase(local, remote)(workMrFox.id, context).data
        assertThat(data).isEqualTo(editionMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionUseCaseGivesCorrectResultOnLocalRemoteFailureSuccess() = runTest {
        val repo = MockLocalRemoteFailureLibraryRepositoryImpl()
        val local = GetEditionLocallyUseCase(repo)
        val remote = GetEditionRemotelyUseCase(repo, MockTrueNetworkProvider)

        val data = GetEditionUseCase(local, remote)(workMrFox.id, context)
        assertThat(data).isInstanceOf(Result.Error::class.java)
    }
}