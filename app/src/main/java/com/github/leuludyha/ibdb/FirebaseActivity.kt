package com.github.leuludyha.ibdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.concurrent.CompletableFuture

class FirebaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IBDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    FirebaseContent()
                }
            }
        }

    }
}

@Composable
fun FirebaseContent() {
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer),
            value = email.value,
            onValueChange = { email.value = it },
            placeholder = { Text(text = "Enter email")}
        )
        TextField(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer),
            value = phone.value,
            onValueChange = { phone.value = it },
            placeholder = { Text(text = "Enter phone number")}
        )
        Row {
            val db: DatabaseReference = Firebase.database.reference
            Button(
                onClick = {
                    val future = CompletableFuture<String>()

                    db.child(phone.value).get().addOnSuccessListener {
                        if (it.value == null) future.completeExceptionally(NoSuchFieldException())
                        else future.complete(it.value as String)
                    }.addOnFailureListener {
                        future.completeExceptionally(it)
                    }

                    future.thenAccept {
                        email.value= it
                    }
                }
            ) {
                Text(text = "Get")
            }
            Button(
                onClick = {
                    db.child(phone.value).setValue(email.value)
                }
            ) {
                Text(text = "Set")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    IBDBTheme {
        FirebaseContent()
    }
}