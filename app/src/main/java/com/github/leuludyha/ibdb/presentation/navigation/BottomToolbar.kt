package com.github.leuludyha.ibdb.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

@Composable
fun BottomToolbar(navController: NavHostController?, defaultSelection: Int = 0) {
    var selectedItem by remember { mutableStateOf(defaultSelection) }

    val tabs = listOf(
        TabDescriptor.Home,
        TabDescriptor.Search,
        TabDescriptor.Collection,
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

@Preview
@Composable
fun DefaultPreview() {
    IBDBTheme { BottomToolbar(null) }
}
