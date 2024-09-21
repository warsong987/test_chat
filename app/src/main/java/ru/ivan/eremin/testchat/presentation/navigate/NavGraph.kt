package ru.ivan.eremin.testchat.presentation.navigate

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ivan.eremin.chat.auth.ui.screen.AuthorizationScreen
import ru.ivan.eremin.navigate.Authorization
import ru.ivan.eremin.navigate.Chat
import ru.ivan.eremin.navigate.Chats
import ru.ivan.eremin.navigate.Profile
import ru.ivan.eremin.navigate.Registration
import ru.ivan.eremin.testchat.presentation.screen.chat.ChatScreen
import ru.ivan.eremin.testchat.presentation.screen.chats.ChatsScreen
import ru.ivan.eremin.testchat.presentation.screen.profile.ProfileScreen
import ru.ivan.eremin.testchat.presentation.screen.registration.RegistrationScreen

@ExperimentalMaterial3Api
@Composable
fun Navigate(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Authorization
    ) {
        composable<Authorization> {
            AuthorizationScreen(
                navHostController = navHostController
            )
        }

        composable<Registration> {
            RegistrationScreen(
                navHostController = navHostController
            )
        }

        composable<Chats> {
            ChatsScreen(
                navHostController = navHostController
            )
        }
        composable<Chat>{
            ChatScreen(navHostController = navHostController)
        }

        composable<Profile> {
            ProfileScreen(
                navHostController = navHostController
            )
        }
    }
}