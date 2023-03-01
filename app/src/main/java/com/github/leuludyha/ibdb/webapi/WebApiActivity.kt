package com.github.leuludyha.ibdb.webapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.leuludyha.ibdb.boredapi.ActivityResponse
import com.github.leuludyha.ibdb.boredapi.BoredApi
import com.github.leuludyha.ibdb.connectivity.ConnectionState
import com.github.leuludyha.ibdb.connectivity.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class WebApiActivity : ComponentActivity() {
    private val viewModel: WebApiActivityViewModel by viewModels {
        ActivityViewModelFactory((application as WebApiApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IBDBTheme {
                WebApiScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
    @Composable
    fun WebApiScreen() {
        // A surface container using the 'background' color from the theme
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            content = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    var activity by remember { mutableStateOf("Great activity to come!") }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ConnectivityStatus()
                        RequestButton(
                            onActivityReceived = {
                                when (it) {
                                    is ActivityResponse.Success ->
                                        activity = it.content
                                    is ActivityResponse.Failure ->
                                        viewModel.getRandomCachedActivity { random -> activity = random.activity }
                                }
                            },
                            onNoConnection = { // show snackbar as a suspend function
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "No internet connection! :("
                                    )
                                }

                                viewModel.getRandomCachedActivity { activity = it.activity }
                            },
                            modifier = Modifier
                                .padding(16.dp)
                        )
                        Text(
                            text = activity,
                            modifier = Modifier.testTag("activity_text")
                        )
                    }
                }
            }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Composable
    fun RequestButton(
        onActivityReceived: (ActivityResponse) -> Unit,
        onNoConnection: () -> Unit,
        modifier: Modifier
    ) {
        val connection by connectivityState()
        val isConnected = connection === ConnectionState.Available

        Button(
            onClick = {
                if (isConnected)
                    viewModel.requestActivity(BoredApi.getInstance(application), onActivityReceived = onActivityReceived)
                else
                    onNoConnection()
            },
            modifier = modifier
                .testTag("ask_activity_button"),
        ) {
            Text("New activity!")
        }
    }

    @ExperimentalCoroutinesApi
    @Composable
    fun ConnectivityStatus() {
        // This will cause re-composition on every network state change
        val connection by connectivityState()

        val isConnected = connection === ConnectionState.Available

        if (isConnected) {
            Text("Connected to Internet!")
        } else {
            Text("No Internet connection!")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun WebApiPreview() {
        IBDBTheme {
            WebApiScreen()
        }
    }
}