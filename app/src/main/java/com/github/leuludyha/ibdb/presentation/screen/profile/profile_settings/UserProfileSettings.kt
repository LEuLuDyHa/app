package com.github.leuludyha.ibdb.presentation.screen.profile.profile_settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.leuludyha.domain.model.user.UserPreferences
import com.github.leuludyha.domain.util.TestTag
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.utils.TextIconButton
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

object TestTags {
    val logoutBtn = TestTag("user-settings-logout-btn")
}

/**
 * Features :
 * - Logout
 * - Delete Account
 * - Notifications settings : Notify for desired book near us
 * - Public Visibility of specific user preferences
 * - Theme : Dark/Bright
 * - Language
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileSettings(
    navController: NavHostController,
    padding: PaddingValues,
    viewModel: UserProfileSettingsViewModel = hiltViewModel()
) {
    val userPreference = viewModel.authContext.principal.preferences

    Scaffold(
        topBar = { SettingsTopBar(navController) },
        modifier = Modifier.padding(padding)
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            DarkModeToggle(userPreference)
            Divider()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DarkModeToggle(
    userPreferences: UserPreferences
) {
    ListItem(
        leadingContent = {
            when (userPreferences.darkMode) {
                true -> Icon(Icons.Filled.DarkMode, stringResource(id = R.string.dark_mode))
                false -> Icon(Icons.Filled.LightMode, stringResource(id = R.string.light_mode))
            }
        },
        headlineText = {
            when (userPreferences.darkMode) {
                true -> Text(text = stringResource(id = R.string.dark_mode))
                false -> Text(text = stringResource(id = R.string.light_mode))
            }
        },
        supportingText = {
            Text(
                text = "Switch to ${
                    when (userPreferences.darkMode) {
                        true -> stringResource(id = R.string.light_mode)
                        false -> stringResource(id = R.string.dark_mode)
                    }
                }"
            )
        },
        modifier = Modifier.selectable(
            selected = userPreferences.darkMode,
            onClick = { userPreferences.darkMode = !userPreferences.darkMode }
        )
    )
}

@Composable
private fun SettingsTopBar(
    navController: NavHostController
) {
    // Logout
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextIconButton(
            icon = Icons.Filled.Logout,
            text = stringResource(id = R.string.icon_logout),
            onClick = { /*TODO*/ },
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    IBDBTheme {
        UserProfileSettings(
            navController = rememberNavController(),
            padding = PaddingValues()
        )
    }
}