@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package ru.ivan.eremin.chat.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.ivan.eremin.chat.profile.entity.Profile
import ru.ivan.eremin.components.BottomNavigate
import ru.ivan.eremin.components.ErrorImage
import ru.ivan.eremin.feature.base.Screen

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsState()

    ProfileScreenState(
        navHostController = navHostController,
        state = state.value,
    )
}

@Composable
private fun ProfileScreenState(
    navHostController: NavHostController,
    state: ProfileUiState
) {
    Screen(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Профиль") },
            )
        },
        bottomBar = {
            BottomNavigate(navController = navHostController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SubcomposeAsyncImage(
                model = state.data?.avatar,
                contentDescription = null,
                error = {
                    ErrorImage()
                },
                loading = {
                    CircularProgressIndicator()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(140.dp)
            )

            Text(text = state.data?.online.toString())
            Text(text = state.data?.name.orEmpty())
            Text(text = state.data?.userName.orEmpty())
            Text(text = state.data?.city.orEmpty())
            Text(text = state.data?.birthday.orEmpty())


        }
    }
}


@Composable
@Preview
private fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreenState(
            navHostController = rememberNavController(),
            state = ProfileUiState(
                data = Profile(
                    avatar = "https://picsum.photos/200",
                    name = "Иван",
                    userName = "ivan",
                    birthday = "01.01.2000",
                    avatars = Profile.Avatar(
                        avatar = "https://picsum.photos/200",
                        bigAvatar = "https://picsum.photos/200",
                        miniAvatar = "https://picsum.photos/200",
                    ),
                    city = "Москва",
                    completedTask = 5,
                    created = "01.01.2000",
                    id = "12324561313",
                    instagram = "https://picsum.photos/200",
                    last = "01.01.2000",
                    online = true,
                    phone = "+7 (999) 999 99 99",
                    status = "online",
                )
            )
        )
    }
}