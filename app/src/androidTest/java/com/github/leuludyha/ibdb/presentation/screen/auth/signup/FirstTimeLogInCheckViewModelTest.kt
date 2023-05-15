package com.github.leuludyha.ibdb.presentation.screen.auth.signup

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.ibdb.util.Constant
import com.github.leuludyha.ibdb.util.Constant.PREFERRED_USERNAME
import com.github.leuludyha.ibdb.util.Constant.SIGN_UP_WALKTHROUGH_COMPLETED
import com.google.common.truth.Truth.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirstTimeLogInCheckViewModelTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun gettingSharedPrefAfterRememberWalkThroughIsCompletedIsTrue() {
        val viewModel = FirstTimeLogInCheckViewModel(Mocks.authContext)

        viewModel.rememberWalkThroughIsCompleted(context)
        val signedUp = context.getSharedPreferences(
            Constant.SIGN_UP_SHARED_PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
            .getBoolean(SIGN_UP_WALKTHROUGH_COMPLETED, false)
        assertThat(signedUp).isTrue()
    }

    @Test
    fun isWalkThroughCompletedAfterRememberWalkThroughIsCompletedIsTrue() {
        val viewModel = FirstTimeLogInCheckViewModel(Mocks.authContext)

        viewModel.rememberWalkThroughIsCompleted(context)
        assertThat(viewModel.isWalkThroughCompleted(context)).isTrue()
    }

    @Test
    fun walkthroughOptionsArePersistedAfterPersistWalkthroughOptions() {
        val viewModel = FirstTimeLogInCheckViewModel(Mocks.authContext)

        val expectedDarkTheme = Mocks.authContext.principal.userPreferences.darkTheme.value
        val expectedUserName = Mocks.authContext.principal.username
        viewModel.persistWalkthroughOptions(context)
        val sp = context.getSharedPreferences(
            Constant.SIGN_UP_SHARED_PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
        val userName = sp
            .getString(PREFERRED_USERNAME, "")
        val darkTheme =  sp
            .getBoolean(
            Constant.DARK_THEME_PREFERENCE,
            !expectedDarkTheme
        )
        assertThat(userName).isEqualTo(expectedUserName)
        assertThat(darkTheme).isEqualTo(expectedDarkTheme)
    }

    @Test
    fun walkthroughOptionsAreUpdatedAfterUpdateWalkthroughPreferences() {
        val viewModel = FirstTimeLogInCheckViewModel(Mocks.authContext)
        val expectedUserName = Mocks.authContext.principal.username
        val expectedDarkTheme = Mocks.authContext.principal.userPreferences.darkTheme.value
        viewModel.persistWalkthroughOptions(context)

        Mocks.authContext.principal.username = "new"
        Mocks.authContext.principal.userPreferences.darkTheme.value = true
        viewModel.updateWalkthroughPreferences(context)

        assertThat(Mocks.authContext.principal.username).isEqualTo(expectedUserName)
        assertThat(Mocks.authContext.principal.userPreferences.darkTheme.value).isEqualTo(expectedDarkTheme)
    }
}