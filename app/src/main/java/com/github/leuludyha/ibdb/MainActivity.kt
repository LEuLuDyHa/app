package com.github.leuludyha.ibdb

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IBDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    val username = remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .testTag("main::text_field")
                .background(MaterialTheme.colorScheme.primaryContainer),
            value = username.value,
            onValueChange = { username.value = it }
        )
        StartButton(
            modifier = Modifier.testTag("main::start_button"),
            username = username.value
        )
    }
}

@Composable
fun StartButton(username: String, modifier: Modifier) {
    val context = LocalContext.current

    val intent = Intent(context, GreetingActivity::class.java)
    intent.putExtra(GreetingActivity.DeclaredIntents.username, username)

    Button(
        onClick = { context.startActivity(intent) },
        modifier
    ) {
        Text(text = "Start !")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IBDBTheme {
        MainContent()
    }
}