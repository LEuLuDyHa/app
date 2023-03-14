package com.github.leuludyha.ibdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.leuludyha.ibdb.presentation.navigation.NavGraph
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { IBDBTheme { MainContent() } }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        IBDBTheme { MainContent() }
    }

    @Composable
    fun MainContent() {
        navController = rememberNavController()
        NavGraph(navController = navController)
    }
}