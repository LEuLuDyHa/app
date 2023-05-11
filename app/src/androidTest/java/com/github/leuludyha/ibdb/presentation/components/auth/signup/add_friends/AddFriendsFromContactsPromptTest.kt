package com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.github.leuludyha.domain.model.library.MockUserRepositoryImpl
import com.github.leuludyha.domain.useCase.users.GetUserFromPhoneNumberUseCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddFriendsFromContactsPromptTest {

    lateinit var viewModel: AddFriendsFromContactsViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val contactsPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.READ_CONTACTS)

    @Before
    fun initViewModel() {
        viewModel = AddFriendsFromContactsViewModel(GetUserFromPhoneNumberUseCase(MockUserRepositoryImpl()))
    }

    @Test
    fun titleHasCorrectText() {
        composeTestRule.setContent {
            AddFriendsFromContactsPrompt.Title()
        }

        composeTestRule.onNodeWithText("Connect with people you know on the app !").assertExists()
    }
}