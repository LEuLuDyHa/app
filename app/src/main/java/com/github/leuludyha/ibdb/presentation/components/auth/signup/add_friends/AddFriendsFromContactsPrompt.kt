package com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.Orientation
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.auth.signup.SignUpPromptBase


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

        val (acquaintances, setAcquaintances) = remember {
            mutableStateOf(listOf<UserFromContact>())
        }

        if (hasContactPermission(context)) {
            // if permission granted open intent to pick contact/
            LaunchedEffect(Unit) {
                // Fetch contacts in the user's phone
                val contacts = viewModel.getContacts(context)
                setAcquaintances(
                    // Set the acquaintances by linking phone number in database with user
                    contacts.zip(viewModel.getPossibleAcquaintances(contacts))
                        .map { UserFromContact(it.first, it.second) }
                        // Remove possible duplicates if contact is present multiple times
                        .distinctBy { it.Id() }
                )
            }
            ItemList(
                values = acquaintances,
                orientation = Orientation.Vertical
            ) {
                MiniContactFoundView(
                    contactName = it.contact.name,
                    user = it.user
                )
            }

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
}