package com.somnwal.app.feature.test.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.somnwal.app.feature.test.TestRoute

fun NavController.navigateToTest(navOptions: NavOptions) {
    navigate(TestRoute.ROUTE, navOptions)
}

fun NavGraphBuilder.testNavGraph(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) {
    composable(route = TestRoute.ROUTE) {
        TestRoute(
            padding = padding,
            onShowErrorSnackbar = onShowErrorSnackbar,
        )
    }
}

object TestRoute {
    const val ROUTE = "test"
}