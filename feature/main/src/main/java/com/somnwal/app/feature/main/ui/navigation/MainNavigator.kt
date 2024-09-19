package com.somnwal.app.feature.main.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.somnwal.app.feature.working.navigation.navigateToWorking

internal class MainNavigator(
    val navController: NavHostController
) {
    private val showAsFullScreenList = listOf(
        MainTab.Working.route
    )

    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = MainTab.Working.route

    val currentTab: MainTab?
        @Composable get() = currentDestination
            ?.route
            ?.let(MainTab.Companion::find)

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            // 시작화면 까지 스택을 전부 팝업한다.
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }

        when(tab) {
            MainTab.Working -> navController.navigateToWorking(navOptions)
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    @Composable
    fun shouldShowAppBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute !in showAsFullScreenList
    }

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute !in showAsFullScreenList
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}