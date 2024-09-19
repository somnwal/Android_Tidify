package com.somnwal.app.core.designsystem.component.canvas.data

import androidx.compose.ui.geometry.Offset

data class Node(
    val id: Int,
    var position: Offset,
    var scale: Float = 0f,
    var content: String = "New Node"
)

