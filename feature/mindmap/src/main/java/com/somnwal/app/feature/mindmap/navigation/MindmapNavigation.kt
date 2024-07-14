package com.somnwal.app.feature.mindmap.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.somnwal.app.feature.mindmap.MindMapRoute

fun NavController.navigateToMindMap(navOptions: NavOptions) {
    navigate(MindMapRoute.ROUTE, navOptions)
}

fun NavGraphBuilder.mindMapNavGraph(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) {
    composable(route = MindMapRoute.ROUTE) {
        MindMapRoute(
            padding = padding,
            onShowErrorSnackbar = onShowErrorSnackbar,
        )
    }
}

object MindMapRoute {
    const val ROUTE = "mindmap"
}