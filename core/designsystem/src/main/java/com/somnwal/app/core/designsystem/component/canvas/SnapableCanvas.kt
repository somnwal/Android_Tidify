package com.somnwal.app.core.designsystem.component.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs

@Composable
fun SnapableCanvas() {
    var srcPosition by remember { mutableStateOf(Offset(100f, 200f)) }
    var destPosition by remember { mutableStateOf(Offset(300f, 300f)) }
    val rectSize = Size(100f, 100f)
    val snapThreshold = 20f

    Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                val newPosition = srcPosition + dragAmount

                // Apply snapping logic
                val snappedPosition = snapRectangles(newPosition, destPosition, rectSize, snapThreshold)

                srcPosition = snappedPosition
            }
        }
    ) {
        // Draw rectangles
        drawRect(Color.Blue, topLeft = srcPosition, size = rectSize)
        drawRect(Color.Red, topLeft = destPosition, size = rectSize)
    }
}

fun snapRectangles(movingRect: Offset, staticRect: Offset, rectSize: Size, threshold: Float): Offset {
    val snapPoints = listOf(
        staticRect.x - rectSize.width,  // Left edge
        staticRect.x + rectSize.width,  // Right edge
        staticRect.y - rectSize.height, // Top edge
        staticRect.y + rectSize.height  // Bottom edge
    )

    val snappedX = snapToPoint(movingRect.x, snapPoints.take(2), threshold)
    val snappedY = snapToPoint(movingRect.y, snapPoints.drop(2), threshold)

    return Offset(snappedX, snappedY)
}

fun snapToPoint(value: Float, points: List<Float>, threshold: Float): Float {
    return points.firstOrNull { abs(it - value) < threshold } ?: value
}