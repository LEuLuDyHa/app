package com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.auth.signup.SignUpPromptBase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Display a component which prompts the user to
 * add friends who already downloaded the application and are
 * in their contacts
 */
object AddFriendsFromContactsPrompt : SignUpPromptBase() {

    @Composable
    override fun Content(
        authContext: AuthenticationContext,
        onComplete: () -> Unit,
    ) {
        val viewModel: AddFriendsFromContactsViewModel = hiltViewModel()

        val context = LocalContext.current

        if (hasContactPermission(context)) {
            // if permission granted open intent to pick contact/
            TODO("Not yet implemented")
        } else {
            // if permission not granted requesting permission .
            requestContactPermission(context, activity = context as Activity)
        }
    }

    @Composable
    override fun Title(
        authContext: AuthenticationContext
    ) {
        DefaultTitle(text = stringResource(id = R.string.prompt_add_friends_title))
    }

    @HiltViewModel
    class AddFriendsFromContactsViewModel @Inject constructor(

    ) : ViewModel() {

        /**
         * Find all contacts of the user, and map them to either a phone number or an email
         */
        fun getContacts(): List<Contact> {
            TODO("Not yet implemented")
        }

        /**
         * Return the list of existing users in the database which this user make know
         */
        fun getPossibleAcquaintances(contacts: List<Contact>): List<User> {
            TODO("Not yet implemented")
        }
    }

    data class Contact(
        val phoneNumber: String?,
        val email: String?,
    ) {

        private val googleEmailSuffix: String = "@gmail.com"

        /**
         * @return True if the email is not null and it ends with [googleEmailSuffix]
         * (It can be used by one of the existing user in the database)
         */
        fun isEmailValid() = email?.endsWith(googleEmailSuffix) == true

        /**
         * @return True if the phone number is not null nor blank, false otherwise
         */
        fun isPhoneNumberValid() = !phoneNumber.isNullOrBlank()

        /**
         * @return True if either the phone number or the email is not null
         */
        fun isValid() = isEmailValid() or isPhoneNumberValid()
    }

}