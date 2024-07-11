package com.somnwal.app.core.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingBar(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true
) {
    if(isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 100,
    heightDp = 100,
)
@Composable
internal fun LoadingBarPreview() {
    LoadingBar()
}