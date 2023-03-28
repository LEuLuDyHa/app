package com.github.leuludyha.ibdb.presentation.screen.profile.profile_settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme
import com.github.leuludyha.ibdb.util.EmptyNavHostController

object TestTags

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
) {
    Scaffold(
        topBar = { SettingsTopBar(navController) },
        modifier = Modifier.padding(padding)
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {


            // At the end
            Button(
                onClick = { /* TODO */ },
            ) {
                Text(text = "Hey")
            }
        }
    }
}

@Composable
private fun SettingsTopBar(
    navController: NavHostController
) {
    // Logout
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { /*TODO*/ }, colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(Icons.Filled.Logout, stringResource(id = R.string.icon_logout))
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    IBDBTheme {
        UserProfileSettings(
            navController = EmptyNavHostController(),
            padding = PaddingValues()
        )
    }
}