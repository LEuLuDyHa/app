package com.github.leuludyha.ibdb.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController

@Composable
fun BottomToolbar(navController: NavHostController?, defaultSelection: Int = 0) {
    var selectedItem by remember { mutableStateOf(defaultSelection) }

    val tabs = listOf(
        TabDescriptor.Home,
        TabDescriptor.Search,
        TabDescriptor.Maps,
        TabDescriptor.Profile
    )

    BottomAppBar {
        NavigationBar {
            tabs.forEachIndexed { index, tab ->
                NavigationBarItem(
                    icon = { Icon(tab.displayIcon, tab.displayName) },
                    label = { Text(tab.displayName) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController?.let { tab.onClick(it) }
                    },
                    modifier = Modifier.testTag("bottomtoolbar::tab_item::${tab.displayName}")
                )
            }
        }
    }
}


sealed class TabDescriptor(
    val displayName: String,
    val displayIcon: ImageVector,
    val onClick: (navController: NavHostController) -> Unit = { },
) {
    // List of all bottom tabs
    object Home : TabDescriptor(
        "Home", Icons.Filled.Home,
        onClick = { navHost ->
            navHost.navigate(route = Screen.Home.route)
        }
    )

    object Search : TabDescriptor(
        "Search", Icons.Filled.Search,
        onClick = { navHost ->
            navHost.navigate(route = Screen.BookSearch.route)
        }
    )

    object Collection : TabDescriptor(
        "Collection", Icons.Filled.LibraryBooks,
        onClick = { navHost ->
            navHost.navigate(route = Screen.Collection.route)
        }
    )

    object Maps : TabDescriptor(
        "Maps", Icons.Filled.Map,
        onClick = { navHost ->
            navHost.navigate(route = Screen.GoogleMaps.route)
        }
    )

    object Profile : TabDescriptor(
        "Profile", Icons.Filled.AccountCircle,
        onClick = { navHost ->
            navHost.navigate(route = Screen.UserProfile.route)
        }
    )
}
