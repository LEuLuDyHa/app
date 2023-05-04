package com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.github.leuludyha.domain.model.library.MockUserRepositoryImpl
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.useCase.users.GetUserFromPhoneNumberUseCase
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddFriendsFromContactsViewModelTest {

    lateinit var viewModel: AddFriendsFromContactsViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @JvmField
    @Rule
    val contactsPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.READ_CONTACTS)

    @Before
    fun initViewModel() {
        viewModel = AddFriendsFromContactsViewModel(GetUserFromPhoneNumberUseCase(MockUserRepositoryImpl()))
    }

    @Test
    fun getContactsDoesntCrashAndNotNull() {
        val contacts = viewModel.getContacts(InstrumentationRegistry.getInstrumentation().context)
        assertThat(contacts).isNotNull()
    }

    // TODO CHANGE TO NON-MOCK WHEN IMPLENENTED
    @Test
    fun getPossibleAcquaintancesReturnsMockUser() {
        val contacts = viewModel.getPossibleAcquaintances(listOf(Contact("name", "phone", "email")))
        assertThat(contacts).isEqualTo(List(contacts.size) { Mocks.mainUser })
    }
}