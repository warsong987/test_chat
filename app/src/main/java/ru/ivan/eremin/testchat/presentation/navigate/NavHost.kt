package ru.ivan.eremin.testchat.presentation.navigate

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.channels.Channel

@Composable
fun NavHost(
    navController: NavHostController,
    startRoute: Routes,
    modifier: Modifier = Modifier,
    router: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startRoute.fullRouter,
        modifier = modifier,
        route = router,
        builder = builder
    )
}

fun NavGraphBuilder.composable(
    route: Routes,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route.fullRouter,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content
    )
}

interface AppNavigate {
    val navController: Channel<NavigationIntent>

    suspend fun navigateBack(
        route: String? = null,
        inclusive: Boolean = false
    )

    fun tryNavigationBack(
        route: String? = null,
        inclusive: Boolean = false
    )

    suspend fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false
    )

    fun tryNavigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false
    )
}

sealed class NavigationIntent {
    data class NavigateBack(
        val route: String? = null,
        val inclusive: Boolean = false
    ) : NavigationIntent()

    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false
    ) : NavigationIntent()
}