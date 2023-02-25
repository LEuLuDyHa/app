package com.github.leuludyha.ibdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

class GreetingActivity : ComponentActivity() {

    object DeclaredIntents {
        const val username: String = "Username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IBDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Find the intent from the MainActivity
                    val context = LocalContext.current
                    val intent = (context as GreetingActivity).intent
                    val name = intent.getStringExtra(DeclaredIntents.username)

                    // And greet user
                    Greeting(name)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String?) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello $name!")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    IBDBTheme {
        Greeting("Android")
    }
}