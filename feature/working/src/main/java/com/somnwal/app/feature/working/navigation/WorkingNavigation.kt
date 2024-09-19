package com.somnwal.app.feature.working.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.somnwal.app.feature.working.WorkingRoute

fun NavController.navigateToWorking(navOptions: NavOptions) {
    navigate(WorkingRoute.ROUTE, navOptions)
}

fun NavGraphBuilder.workingNavGraph(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) {
    composable(route = WorkingRoute.ROUTE) {
        WorkingRoute(
            padding = padding,
            onShowErrorSnackbar = onShowErrorSnackbar,
        )
    }
}

object WorkingRoute {
    const val ROUTE = "working"
}