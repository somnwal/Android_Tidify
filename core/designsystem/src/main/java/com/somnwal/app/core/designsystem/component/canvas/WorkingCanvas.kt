package com.somnwal.app.core.designsystem.component.canvas

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

data class Node(
    val id: Int,
    var position: Offset,
    var scale: Float = 0f,
    var content: String = "New Node"
)

data class Arrow(val start: Node, val end: Node)

@Composable
fun WorkingCanvas() {
    var nodes by remember { mutableStateOf(listOf<Node>()) }
    var arrows by remember { mutableStateOf(listOf<Arrow>()) }
    var nextNodeId by remember { mutableIntStateOf(0) }

    // 노드 드래그 변경
    var dragStartNode by remember { mutableStateOf<Node?>(null) }
    var dragEndPosition by remember { mutableStateOf<Offset?>(null) }
    var movingNode by remember { mutableStateOf<Node?>(null) }
    var isDrawingArrow by remember { mutableStateOf(false) }
    var longPressJob by remember { mutableStateOf<Job?>(null) }

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var showGrid by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(nodes) {
        launch {
            while(true) {
                val updatedNodes = applyRepulsion(nodes)
                val updatedArrows = arrows.map { arrow ->
                    val newStart = updatedNodes.find { it.id == arrow.start.id } ?: arrow.start
                    val newEnd = updatedNodes.find { it.id == arrow.end.id } ?: arrow.end
                    arrow.copy(start = newStart, end = newEnd)
                }
                nodes = updatedNodes
                arrows = updatedArrows
                delay(16)
            }
        }
    }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offset.x * scale,
                translationY = offset.y * scale
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale *= zoom
                    offset += pan / scale
                }
                detectDragGestures(
                    onDragStart = { startOffset ->
                        val clickedNode = nodes.find {
                            ((it.position - (startOffset - offset) / scale).getDistance() < 50f / scale)
                        }
                        if (clickedNode != null) {
                            dragStartNode = clickedNode
                            isDrawingArrow = true
                            longPressJob = coroutineScope.launch {
                                delay(500) // 0.5초로 변경
                                if (dragStartNode == clickedNode) {
                                    movingNode = clickedNode
                                    isDrawingArrow = false
                                }
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val scaledDragAmount = dragAmount / scale
                        if (movingNode != null) {
                            nodes = nodes.map {
                                if (it.id == movingNode!!.id) it.copy(position = it.position + scaledDragAmount)
                                else it
                            }
                            arrows = arrows.map { arrow ->
                                when {
                                    arrow.start.id == movingNode!!.id -> arrow.copy(start = nodes.find { it.id == movingNode!!.id }!!)
                                    arrow.end.id == movingNode!!.id -> arrow.copy(end = nodes.find { it.id == movingNode!!.id }!!)
                                    else -> arrow
                                }
                            }
                        } else if (dragStartNode != null && isDrawingArrow) {
                            dragEndPosition = (change.position - offset) / scale
                        }
                    },
                    onDragEnd = {
                        longPressJob?.cancel()
                        if (isDrawingArrow && dragStartNode != null && dragEndPosition != null) {
                            val endNode = nodes.find {
                                ((it.position - dragEndPosition!!).getDistance() < 50f / scale)
                            }
                            if (endNode != null && endNode != dragStartNode) {
                                arrows = arrows + Arrow(dragStartNode!!, endNode)
                            }
                        }
                        dragStartNode = null
                        dragEndPosition = null
                        movingNode = null
                        isDrawingArrow = false
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { tapOffset ->
                        val newNodePosition = (tapOffset - offset) / scale
                        val newNode = Node(nextNodeId++, newNodePosition)
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
        if (showGrid) {
            drawGrid(scale)
        }

        arrows.forEach { arrow ->
            val startPoint = getEdgePoint(arrow.start, arrow.end.position)
            val endPoint = getEdgePoint(arrow.end, arrow.start.position)
            drawArrow(startPoint, endPoint, Color(0xFF03DAC5))
        }

        nodes.forEach { node ->
            drawRoundRect(
                color = Color(0xFF6200EE),
                topLeft = node.position - Offset(40f, 25f),
                size = Size(80f, 50f),
                cornerRadius = CornerRadius(8.dp.toPx() / scale),
                style = Stroke(width = 2.dp.toPx() / scale)
            )
        }

        if (dragStartNode != null && dragEndPosition != null && isDrawingArrow) {
            val startPoint = getEdgePoint(dragStartNode!!, dragEndPosition!!)
            val endNode = nodes.find { node ->
                (node.position - dragEndPosition!!).getDistance() < 50f / scale && node.id != dragStartNode!!.id
            }
            val endPoint = if (endNode != null) {
                getEdgePoint(endNode, dragStartNode!!.position)
            } else {
                dragEndPosition!!
            }
            val arrowColor = if (endNode != null) Color.Green else Color(0xFFFF4081)
            drawArrow(startPoint, endPoint, arrowColor)
        }
    }
}

fun DrawScope.drawGrid(scale: Float) {
    val gridSize = 50f
    val scaledGridSize = gridSize * scale

    val horizontalLineCount = (size.height / scaledGridSize).toInt() + 1
    val verticalLineCount = (size.width / scaledGridSize).toInt() + 1

    repeat(horizontalLineCount) { i ->
        val y = i * scaledGridSize
        drawLine(
            Color.LightGray.copy(alpha = 0.3f),
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = 1f
        )
    }

    repeat(verticalLineCount) { i ->
        val x = i * scaledGridSize
        drawLine(
            Color.LightGray.copy(alpha = 0.3f),
            start = Offset(x, 0f),
            end = Offset(x, size.height),
            strokeWidth = 1f
        )
    }
}

fun DrawScope.drawArrow(start: Offset, end: Offset, color: Color) {
    val arrowLength = 20f
    val angle = 25f


    val dx = end.x - start.x
    val dy = end.y - start.y
    val theta = atan2(dy, dx)

    drawLine(color, start, end, strokeWidth = 2.dp.toPx())

    val x1 = end.x - arrowLength * cos(theta - Math.toRadians(angle.toDouble())).toFloat()
    val y1 = end.y - arrowLength * sin(theta - Math.toRadians(angle.toDouble())).toFloat()
    drawLine(color, end, Offset(x1, y1), strokeWidth = 2.dp.toPx())

    val x2 = end.x - arrowLength * cos(theta + Math.toRadians(angle.toDouble())).toFloat()
    val y2 = end.y - arrowLength * sin(theta + Math.toRadians(angle.toDouble())).toFloat()
    drawLine(color, end, Offset(x2, y2), strokeWidth = 2.dp.toPx())
}

fun getEdgePoint(node: Node, targetPosition: Offset): Offset {
    val nodeCenter = node.position
    val nodeWidth = 80f
    val nodeHeight = 50f

    val dx = targetPosition.x - nodeCenter.x
    val dy = targetPosition.y - nodeCenter.y

    val angle = atan2(dy, dx)

    // 노드의 네 모서리 좌표 계산
    val topLeft = nodeCenter - Offset(nodeWidth / 2, nodeHeight / 2)
    val topRight = topLeft + Offset(nodeWidth, 0f)
    val bottomLeft = topLeft + Offset(0f, nodeHeight)
    val bottomRight = topLeft + Offset(nodeWidth, nodeHeight)

    // 선분과 노드의 각 변이 만나는 점 계산
    val intersections = listOf(
        lineIntersection(nodeCenter, targetPosition, topLeft, topRight),
        lineIntersection(nodeCenter, targetPosition, topRight, bottomRight),
        lineIntersection(nodeCenter, targetPosition, bottomLeft, bottomRight),
        lineIntersection(nodeCenter, targetPosition, topLeft, bottomLeft)
    ).filterNotNull()

    // 노드 중심에서 가장 가까운 교차점 반환
    return intersections.minByOrNull { (it - nodeCenter).getDistance() } ?: nodeCenter
}

fun lineIntersection(p1: Offset, p2: Offset, p3: Offset, p4: Offset): Offset? {
    val d = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x)
    if (d == 0f) return null

    val t = ((p1.x - p3.x) * (p3.y - p4.y) - (p1.y - p3.y) * (p3.x - p4.x)) / d
    val u = -((p1.x - p2.x) * (p1.y - p3.y) - (p1.y - p2.y) * (p1.x - p3.x)) / d

    if (t in 0f..1f && u in 0f..1f) {
        return Offset(p1.x + t * (p2.x - p1.x), p1.y + t * (p2.y - p1.y))
    }

    return null
}

// 노드 간 반발력을 계산하고 적용하는 함수
fun applyRepulsion(nodes: List<Node>): List<Node> {
    val repulsionStrength = 5000f // 반발력 강도
    val minDistance = 300f // 최소 거리

    return nodes.map { node ->
        var forcex = 0f
        var forcey = 0f

        nodes.forEach { otherNode ->
            if (node.id != otherNode.id) {
                val dx = node.position.x - otherNode.position.x
                val dy = node.position.y - otherNode.position.y
                val distanceSquared = dx.pow(2) + dy.pow(2)
                val distance = sqrt(distanceSquared)

                if (distance < minDistance) {
                    val repulsionForce = repulsionStrength / distanceSquared
                    forcex += repulsionForce * dx / distance
                    forcey += repulsionForce * dy / distance
                }
            }
        }

        // 힘을 position에 적용
        val newPosition = Offset(
            node.position.x + forcex.coerceIn(-5f, 5f),
            node.position.y + forcey.coerceIn(-5f, 5f)
        )

        node.copy(position = newPosition)
    }
}