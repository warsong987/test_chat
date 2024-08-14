@file:OptIn(ExperimentalMaterial3Api::class)

package ru.ivan.eremin.testchat.presentation.screen.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import ru.ivan.eremin.testchat.presentation.components.BottomNavigate
import ru.ivan.eremin.testchat.presentation.components.Screen

@Composable
fun ProfileScreen(
    navHostController: NavHostController
) {
    Screen(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigate(navController = navHostController)
        }
    ) {
        Text(text = "Профиль")
    }
}