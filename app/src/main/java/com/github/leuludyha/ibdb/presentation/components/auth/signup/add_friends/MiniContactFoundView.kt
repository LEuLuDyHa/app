package com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.screen.profile.defaultProfilePicture

@Composable
fun MiniContactFoundView(
    contactName: String,
    user: User,
) {
    val (requestSent, setRequestSent) = remember { mutableStateOf(false) }

    fun sendFriendRequest() {
        // TODO
        setRequestSent(true)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // User display
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    modifier = Modifier.size(80.dp),
                    painter = rememberImagePainter(
                        // Take the member's google account's picture for now
                        user.profilePictureUrl() ?: defaultProfilePicture
                    ),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Fit,
                )
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .wrapContentWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = contactName, style = MaterialTheme.typography.labelLarge)
                    Text(text = user.username(), style = MaterialTheme.typography.labelMedium)
                }
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxHeight()
                    .size(80.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                // Add as friend
                IconButton(
                    onClick = { sendFriendRequest() },
                    enabled = !requestSent,

                    ) {
                    Icon(
                        Icons.Filled.PersonAdd,
                        stringResource(id = R.string.friends_add),
                        tint = if (requestSent)
                            MaterialTheme.colorScheme.secondary
                        else
                            MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}