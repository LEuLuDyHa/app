package com.github.leuludyha.ibdb.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

@Composable
fun BottomToolbar(defaultSelection: Int = 0) {
    var selectedItem by remember { mutableStateOf(defaultSelection) }
    val items = listOf(
        Pair("Home", Icons.Filled.Home),
        Pair("Discovery", Icons.Filled.Explore),
        Pair("Collection", Icons.Filled.LibraryBooks),
        Pair("Profile", Icons.Filled.AccountCircle),
    )

    BottomAppBar {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(item.second, item.first) },
                    label = { Text(item.first) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index }
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    IBDBTheme {
        BottomToolbar()
    }
}
