package com.github.leuludyha.ibdb.presentation.screen.auth.signup

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.ibdb.presentation.screen.auth.signup.FirstTimeLogInCheck
import com.github.leuludyha.ibdb.presentation.screen.auth.signup.FirstTimeLogInCheckViewModel
import com.google.common.truth.Truth.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirstTimeLogInCheckTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun screenDoesNotCrash() {
        composeTestRule.setContent {
            FirstTimeLogInCheck(
                viewModel = FirstTimeLogInCheckViewModel(Mocks.authContext),
            ){ }
        }

        assertThat(true).isTrue()
    }
}