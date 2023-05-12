package com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.github.leuludyha.domain.model.library.Mocks
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddFriendsHelperTest {

    @get:Rule
    val contactsPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.READ_CONTACTS)

    @Test
    fun hasContactsPermissionIsTrueWithPermission() {
        assertThat(hasContactPermission(InstrumentationRegistry.getInstrumentation().context))
            .isTrue()
    }

    // TODO cannot revoke the permission to test without it... because true by default

    @Test
    fun userFromContactFieldsAreCorrectlyAccessed() {
        val contact = Contact("name", "phone", "mail")
        val user = Mocks.mainUser
        val ufc = UserFromContact(contact, user)

        assertThat(ufc.contact).isEqualTo(contact)
        assertThat(ufc.user).isEqualTo(user)
        assertThat(ufc.Id()).isEqualTo(user.Id())
    }

    @Test
    fun contactFieldsAreCorrectlyAccessed() {
        val contact = Contact("name", "phone", "mail")

        assertThat(contact.email).isEqualTo("mail")
        assertThat(contact.name).isEqualTo("name")
        assertThat(contact.phoneNumber).isEqualTo("phone")
    }

    @Test
    fun contactIsEmailValidReturnsTrueOnValidEmail() {
        val contact = Contact("name", "phone", "mail@gmail.com")
        assertThat(contact.isEmailValid()).isTrue()
    }

    @Test
    fun contactIsPhoneNumberValidReturnsTrueOnValidPhoneNumber() {
        val contact = Contact("name", "0787568205", "mail@gmail.com")
        assertThat(contact.isPhoneNumberValid()).isTrue()
    }

    @Test
    fun contactIsValidReturnsTrueOnValidContact() {
        val contact = Contact("name", "0787568205", "mail@gmail.com")
        assertThat(contact.isValid()).isTrue()
    }

    @Test
    fun contactIsEmailValidReturnsFalseOnInvalidEmail() {
        val contact = Contact("name", "phone", "mail")
        assertThat(contact.isEmailValid()).isFalse()
    }

    @Test
    fun contactIsEmailValidReturnsFalseOnNullEmail() {
        val contact = Contact("name", "phone", null)
        assertThat(contact.isEmailValid()).isFalse()
    }

    @Test
    fun contactIsPhoneNumberValidReturnsFalseOnBlankPhoneNumber() {
        val contact = Contact("name", "", "mail")
        assertThat(contact.isPhoneNumberValid()).isFalse()
    }

    @Test
    fun contactIsPhoneNumberValidReturnsFalseOnNullPhoneNumber() {
        val contact = Contact("name", null, "mail")
        assertThat(contact.isPhoneNumberValid()).isFalse()
    }

    @Test
    fun contactIsValidReturnsFalseOnInvalidContact() {
        val contact = Contact("name", "", "mail")
        assertThat(contact.isValid()).isFalse()
    }
}