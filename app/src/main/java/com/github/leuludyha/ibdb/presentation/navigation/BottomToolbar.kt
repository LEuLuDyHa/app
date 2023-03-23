package com.github.leuludyha.ibdb.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

// List of all bottom tabs
val Home = TabDescriptor(
    "Home", Icons.Filled.Home
) { navHost ->
    navHost.navigate(route = Screen.Home.route)
}

val Search = TabDescriptor(
    "Search", Icons.Filled.Search
) { navHost ->
    navHost.navigate(route = Screen.BookSearch.route)
}

val Collection = TabDescriptor(
    "Collection", Icons.Filled.LibraryBooks
)

val Profile = TabDescriptor(
    "Profile", Icons.Filled.AccountCircle
)

@Composable
fun BottomToolbar(navController: NavHostController?, defaultSelection: Int = 0) {
    var selectedItem by remember { mutableStateOf(defaultSelection) }

    val tabs = listOf(
        Home, Search, Collection, Profile
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

data class TabDescriptor(
    val displayName: String,
    val displayIcon: ImageVector,
    val onClick: (navController: NavHostController) -> Unit = { },
)

@Preview
@Composable
fun DefaultPreview() {
    IBDBTheme {
        BottomToolbar(null)
    }
}
