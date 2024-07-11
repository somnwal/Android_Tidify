package com.somnwal.app.feature.main.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.somnwal.app.core.designsystem.component.AppIcons
import com.somnwal.app.feature.test.navigation.TestRoute

internal enum class MainTab(
    val route: String,
    internal val contentDescription: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    TEST(
        route = TestRoute.ROUTE,
        contentDescription = "테스트",
        unselectedIcon = AppIcons.ICON_TEST_OUTLINED,
        selectedIcon = AppIcons.ICON_TEST_FILLED
    );

    companion object {
        operator fun contains(route: String): Boolean {
            return entries.map { it.route }.contains(route)
        }

        fun find(route: String): MainTab? {
            return entries.find { it.route == route }
        }
    }
}