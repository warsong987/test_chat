package ru.ivan.eremin.testchat.presentation.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ivan.eremin.testchat.presentation.screen.authorization.AuthorizationScreen
import ru.ivan.eremin.testchat.presentation.screen.chat.ChatScreen
import ru.ivan.eremin.testchat.presentation.screen.profile.ProfileScreen
import ru.ivan.eremin.testchat.presentation.screen.registration.RegistrationScreen

@Composable
fun Navigate(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Registration.route) {
        composable(Registration.route) { RegistrationScreen(navController) }
        composable(Authorization.route) { AuthorizationScreen(navController) }
        composable(Profile.route) { ProfileScreen(navController) }
        composable(Chats.route) { ChatScreen(navController) }
    }
}