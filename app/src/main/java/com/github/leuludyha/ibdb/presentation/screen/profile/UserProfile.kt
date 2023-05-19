package com.github.leuludyha.ibdb.presentation.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.github.leuludyha.ibdb.util.Constant

@Composable
fun UserProfile(
    navController: NavHostController,
    outerPadding: PaddingValues,
    viewModel: UserProfileViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(outerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .size(154.dp)
                .padding(5.dp),
            shape = CircleShape,
            border = BorderStroke(0.5.dp, Color.Black)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    // Take the member's google account's picture for now
                    viewModel.authContext.principal.profilePictureUrl
                ),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            text = viewModel.authContext.principal.username,
            fontSize = 22.sp,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(50.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Button(Constant.USER_PROFILE_MY_FRIENDS)
            Button(Constant.USER_PROFILE_RECEIVE_WORK) {
                // Start listening for people sharing other works
                navController.navigate(Screen.ReceiveNearbyWork.route)
            }
            // Button(Constant.USER_PROFILE_SETTINGS)
        }
    }
}

@Composable
private fun Button(text: String, onClick: () -> Unit = {}) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) { Text(text) }
}

