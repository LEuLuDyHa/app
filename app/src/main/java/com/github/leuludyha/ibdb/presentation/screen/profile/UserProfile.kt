package com.github.leuludyha.ibdb.presentation.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.github.leuludyha.ibdb.presentation.components.authentication.DisplayIfAuthenticated

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
        DisplayIfAuthenticated(authContext = viewModel.authContext) {
            Surface(
                modifier = Modifier
                    .size(154.dp)
                    .padding(5.dp),
                shape = CircleShape,
                border = BorderStroke(0.5.dp, Color.Black)
            ) {
                Image(
                    painter = rememberImagePainter( //integrate with user later
                        "https://images.freeimages.com/images/large-previews/023/geek-avatar-1632962.jpg"),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                )
            }
            Text(
                text = (viewModel.authContext.principal?.username ?: "username"),
                fontSize = 22.sp,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Divider(Modifier, 50.dp, Color.Transparent)
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button("My Friends")
                Button("Something")
                Button("Settings")
            }
        }
    }
}

@Composable
private fun Button(text: String){
    Button(
        onClick = { /* Do something */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(text)
    }
}

