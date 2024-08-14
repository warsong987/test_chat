package ru.ivan.eremin.testchat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ivan.eremin.testchat.presentation.navigate.Navigate
import ru.ivan.eremin.testchat.presentation.theme.TestChatTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationController = rememberNavController()
            TestChatTheme {
                Navigate(
                    navController = navigationController,
                )
            }
        }
    }
}
