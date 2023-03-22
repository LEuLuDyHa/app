package com.github.leuludyha.ibdb.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

// List of all bottom tabs
private val Tabs = listOf(
    TabDescriptor(
        "Home", Icons.Filled.Home
    ) { navHost ->
        navHost.navigate(route = Screen.Home.route)
    },
    TabDescriptor(
        "Discovery", Icons.Filled.Explore
    ),
    TabDescriptor(
        "Collection", Icons.Filled.LibraryBooks
    ),
    TabDescriptor(
        "Profile", Icons.Filled.AccountCircle
    ),
)

@Composable
fun BottomToolbar(navController: NavHostController?, defaultSelection: Int = 0) {
    var selectedItem by remember { mutableStateOf(defaultSelection) }

    BottomAppBar {
        NavigationBar {
            Tabs.forEachIndexed { index, tab ->
                NavigationBarItem(
                    icon = { Icon(tab.displayIcon, tab.displayName) },
                    label = { Text(tab.displayName) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController?.let { tab.onClick(it) }
                    }
                )
            }
        }
    }
}

private data class TabDescriptor(
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
