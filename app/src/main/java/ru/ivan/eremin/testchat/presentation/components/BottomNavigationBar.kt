package ru.ivan.eremin.testchat.presentation.components


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.ivan.eremin.testchat.presentation.navigate.itemsBottomBar

@Composable
fun BottomNavigate(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        itemsBottomBar.fastForEachIndexed { i, routes ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == routes.route } == true,
                label = {
                    routes.resourceId?.let { Text(text = stringResource(id = routes.resourceId)) }

                },
                onClick = {
                    navController.navigate(routes.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    routes.iconResourceId?.let {
                        Icon(painter = painterResource(id = it), contentDescription = "")
                    }
                }
            )
        }
    }
}