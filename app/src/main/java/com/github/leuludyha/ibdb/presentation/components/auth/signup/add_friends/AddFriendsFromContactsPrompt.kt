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
import com.github.leuludyha.ibdb.presentation.components.auth.signup.ChangeUsernamePrompt.DefaultDisplay
import com.github.leuludyha.ibdb.presentation.components.auth.signup.ChangeUsernamePrompt.DefaultTitle
import com.github.leuludyha.ibdb.presentation.components.auth.signup.SignUpPrompt


/**
 * Display a component which prompts the user to
 * add friends who already downloaded the application and are
 * in their contacts
 */
object AddFriendsFromContactsPrompt : SignUpPrompt {

    @Composable
    override fun Display(authContext: AuthenticationContext, onComplete: () -> Unit) {
        DefaultDisplay(
            content = {
                Content()
            },
            authContext = authContext,
            onComplete = onComplete
        )
    }

    @Composable
    fun Content(
        viewModel: AddFriendsFromContactsViewModel = hiltViewModel()
    ) {

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
    fun Title() {
        DefaultTitle(text = stringResource(id = R.string.prompt_add_friends_title))
    }
}