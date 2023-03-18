package com.github.leuludyha.ibdb.presentation.screen.auth.signin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.ui.navigation.BottomToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
//    viewModel: SignInViewModel = hiltViewModel()
) {

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
//                        viewModel.oneTapSignIn()
                    }
                ) {
                    Text(text = "Sign In") // TODO some string resource
                }
            }
        },
        bottomBar = { BottomToolbar() }
    )

}
