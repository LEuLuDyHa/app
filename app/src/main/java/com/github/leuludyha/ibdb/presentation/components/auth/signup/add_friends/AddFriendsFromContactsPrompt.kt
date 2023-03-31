package com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.interfaces.Keyed
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.useCase.users.GetUserFromPhoneNumberUseCase
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.Orientation
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.auth.signup.SignUpPromptBase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
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

    @HiltViewModel
    class AddFriendsFromContactsViewModel @Inject constructor(
        private val getUserFromPhoneNumberUseCase: GetUserFromPhoneNumberUseCase
    ) : ViewModel() {

        /**
         * Find all contacts of the user, and map them to either a phone number or an email
         * "Copy-pasted" from https://stackoverflow.com/questions/12562151/android-get-all-contacts
         */
        @SuppressLint("Range")
        fun getContacts(ctx: Context): List<Contact> {
            val result = mutableListOf<Contact>()
            val cr: ContentResolver = ctx.contentResolver
            val cur: Cursor? = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
            )
            if ((cur?.count ?: 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    val id: String = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID)
                    )
                    val name: String = cur.getString(
                        cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME
                        )
                    )
                    if (cur.getInt(
                            cur.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER
                            )
                        ) > 0
                    ) {
                        val pCur: Cursor? = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )
                        if (pCur != null) {
                            while (pCur.moveToNext()) {
                                val phoneNo: String = pCur.getString(
                                    pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER
                                    )
                                )

                                result.add(
                                    Contact(
                                        name = name,
                                        phoneNumber = phoneNo,
                                        email = null
                                    )
                                )
                            }
                            pCur.close()
                        }
                    }
                }
            } else {
                Log.e("FRIENDS", "No Contacts !")
            }
            cur?.close()

            return result
        }

        /**
         * Return the list of existing users in the database which this user make know
         */
        fun getPossibleAcquaintances(contacts: List<Contact>): List<User> {
            // Map phone numbers to users
            val userFutures = contacts
                .filter { it.isPhoneNumberValid() }
                .map { getUserFromPhoneNumberUseCase(it.phoneNumber!!) }

            return CompletableFuture.allOf(*userFutures.toTypedArray())
                .thenApply {
                    userFutures.mapNotNull {
                        try {
                            it.join()
                        }
                        // Just ignore users if not found
                        catch (e: CompletionException) {
                            null
                        }
                    }.toList()
                }.join()
        }
    }

    data class UserFromContact(
        val contact: Contact,
        val user: User,
    ) : Keyed {
        override fun Id() = user.Id()

    }

    data class Contact(
        val name: String,
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