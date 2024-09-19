package com.somnwal.app.feature.working

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.somnwal.app.core.designsystem.component.canvas.SnapableCanvas
import com.somnwal.app.core.designsystem.component.canvas.WorkingCanvas


@Composable
internal fun WorkingRoute(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit
) {
    SnapableCanvas()
}
