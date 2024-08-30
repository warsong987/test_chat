package ru.ivan.eremin.testchat.presentation.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ivan.eremin.testchat.presentation.screen.authorization.AuthorizationScreen
import ru.ivan.eremin.testchat.presentation.screen.chat.ChatScreen
import ru.ivan.eremin.testchat.presentation.screen.chats.ChatsScreen
import ru.ivan.eremin.testchat.presentation.screen.profile.ProfileScreen
import ru.ivan.eremin.testchat.presentation.screen.registration.RegistrationScreen

@Composable
fun Navigate(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = AuthorizationRoute.route
    ) {
        composable(AuthorizationRoute.route) {
            AuthorizationScreen(
                navHostController = navHostController
            )
        }

        composable(RegistrationRoute.route) {
            RegistrationScreen(
                navHostController = navHostController
            )
        }

        composable(ChatsRoute.route) {
            ChatsScreen(
                navHostController = navHostController
            )
        }
        composable(
            ChatRoute.route,
            arguments = listOf(
                navArgument(ChatRoute.ID) {
                    type = NavType.IntType
                }
            )
        ) {
            ChatScreen(navHostController = navHostController)
        }

        composable(ProfileRoute.route) {
            ProfileScreen(
                navHostController = navHostController
            )
        }
    }
}