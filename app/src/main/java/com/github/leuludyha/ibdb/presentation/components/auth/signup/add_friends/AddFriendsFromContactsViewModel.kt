package com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.useCase.users.GetUserFromPhoneNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import javax.inject.Inject

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
        return List(contacts.size) { Mocks.mainUser }
        // TODO fix the firebase bug

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