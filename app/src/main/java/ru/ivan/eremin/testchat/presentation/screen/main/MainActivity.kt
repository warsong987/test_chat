package ru.ivan.eremin.testchat.presentation.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ivan.eremin.testchat.presentation.navigate.Navigate

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    MaterialTheme {
        val navHostController = rememberNavController()
        Navigate(navHostController = navHostController)
    }
}
