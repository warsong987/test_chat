package ru.ivan.eremin.components


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
import ru.ivan.eremin.navigate.itemsBottomBar

@Composable
fun BottomNavigate(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        itemsBottomBar.fastForEachIndexed { i, route ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it == route } == true,
                label = {
                    route.resourceId?.let { Text(text = stringResource(id = route.resourceId)) }

                },
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    route.iconResourceId?.let {
                        Icon(painter = painterResource(id = it), contentDescription = "")
                    }
                }
            )
        }
    }
}