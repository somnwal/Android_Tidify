package com.somnwal.app.feature.mindmap

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class Node(
    val id: Int,
    var position: Offset,
    var scale: Float = 0f,
    var content: String = "New Node"
)

data class Arrow(val start: Offset, val end: Offset)

@Composable
internal fun MindMapRoute(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit
) {
    var nodes by remember { mutableStateOf(listOf<Node>()) }
    var arrows by remember { mutableStateOf(listOf<Arrow>()) }
    var nextNodeId by remember { mutableStateOf(0) }
    var dragStart by remember { mutableStateOf<Offset?>(Offset.Zero) }
    var dragEnd by remember { mutableStateOf<Offset?>(Offset.Zero) }

    var movingNode by remember { mutableStateOf<Node?>(null) }

    val coroutineScope = rememberCoroutineScope()

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val clickedNode = nodes.find { (it.position - offset).getDistance() < 50f }
                        if (clickedNode != null) {
                            dragStart = clickedNode.position
                            movingNode = clickedNode
                        } else {
                            dragStart = offset
                        }
                    },
                    onDragEnd = {
                        if (movingNode == null && dragEnd != Offset.Zero) {
                            val endNode = nodes.find { (it.position - (dragEnd ?: Offset.Zero)).getDistance() < 50f }
                            if (endNode != null) {
                                arrows = arrows + Arrow(dragStart ?: Offset.Zero, endNode.position)
                            }
                        }
                        dragStart = null
                        dragEnd = null
                        movingNode = null
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        if (movingNode != null) {
                            // 노드 이동
                            nodes = nodes.map {
                                if (it.id == movingNode!!.id) it.copy(position = it.position + dragAmount)
                                else it
                            }
                            // 연결된 화살표 업데이트
                            arrows = arrows.map { arrow ->
                                when {
                                    arrow.start == movingNode!!.position -> arrow.copy(start = arrow.start + dragAmount)
                                    arrow.end == movingNode!!.position -> arrow.copy(end = arrow.end + dragAmount)
                                    else -> arrow
                                }
                            }
                        } else {
                            dragEnd = change.position
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { offset ->
                        val newNode = Node(nextNodeId++, offset)
                        nodes = nodes + newNode
                        coroutineScope.launch {
                            animate(0f, 1f, animationSpec = tween(durationMillis = 300)) { value, _ ->
                                nodes = nodes.map { if (it.id == newNode.id) it.copy(scale = value) else it }
                            }
                        }
                    }
                )
            }
    ) {
        nodes.forEach { node ->
            drawRoundRect(
                color = Color(0xFF6200EE),
                topLeft = node.position - Offset(40f * node.scale, 25f * node.scale),
                size = Size(80f * node.scale, 50f * node.scale),
                cornerRadius = CornerRadius(8.dp.toPx()),
                style = Stroke(width = 2.dp.toPx())
            )
        }

        arrows.forEach { arrow ->
            drawArrow(arrow.start, arrow.end, Color(0xFF03DAC5))
        }

        // 드래그 중인 임시 화살표 그리기
        if (dragStart != Offset.Zero && dragEnd != Offset.Zero && movingNode == null) {
            drawArrow(dragStart, dragEnd, Color(0xFFFF4081))
        }
    }
}

fun DrawScope.drawArrow(start: Offset?, end: Offset?, color: Color) {

    if(start == null || end == null) {
        return
    }

    val arrowLength = 20f
    val angle = 25f

    val dx = end.x - start.x
    val dy = end.y - start.y
    val theta = kotlin.math.atan2(dy, dx)

    val path = Path().apply {
        moveTo(start.x, start.y)
        lineTo(end.x, end.y)
        moveTo(end.x, end.y)
        lineTo(
            (end.x - arrowLength * kotlin.math.cos(theta - Math.toRadians(angle.toDouble()))).toFloat(),
            (end.y - arrowLength * kotlin.math.sin(theta - Math.toRadians(angle.toDouble()))).toFloat()
        )
        moveTo(end.x, end.y)
        lineTo(
            (end.x - arrowLength * kotlin.math.cos(theta + Math.toRadians(angle.toDouble()))).toFloat(),
            (end.y - arrowLength * kotlin.math.sin(theta + Math.toRadians(angle.toDouble()))).toFloat()
        )
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = 2.dp.toPx())
    )
}