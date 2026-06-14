package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PrintPreviewOverlay(
    modifier: Modifier = Modifier,
    paperWidthMm: Float = 210f, // A4
    paperHeightMm: Float = 297f, // A4
    bleedMm: Float = 3f,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        content()

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val rulerSize = 24.dp.toPx()
            
            // Draw Ruler Background
            drawRect(
                color = Color.White.copy(alpha = 0.9f),
                topLeft = Offset(0f, 0f),
                size = Size(width, rulerSize)
            )
            drawRect(
                color = Color.White.copy(alpha = 0.9f),
                topLeft = Offset(0f, 0f),
                size = Size(rulerSize, height)
            )

            // Scale to fit width
            val mmToPx = (width - rulerSize) / paperWidthMm
            val tickColor = Color.DarkGray

            // Top Ruler Marks
            for (i in 0..paperWidthMm.toInt()) {
                val x = rulerSize + (i * mmToPx)
                val tickHeight = when {
                    i % 10 == 0 -> 12.dp.toPx()
                    i % 5 == 0 -> 8.dp.toPx()
                    else -> 4.dp.toPx()
                }
                drawLine(
                    color = tickColor,
                    start = Offset(x, rulerSize - tickHeight),
                    end = Offset(x, rulerSize),
                    strokeWidth = if (i % 10 == 0) 2f else 1f
                )
            }

            // Left Ruler Marks
            val heightInMm = (height - rulerSize) / mmToPx
            for (i in 0..heightInMm.toInt()) {
                val y = rulerSize + (i * mmToPx)
                val tickWidth = when {
                    i % 10 == 0 -> 12.dp.toPx()
                    i % 5 == 0 -> 8.dp.toPx()
                    else -> 4.dp.toPx()
                }
                drawLine(
                    color = tickColor,
                    start = Offset(rulerSize - tickWidth, y),
                    end = Offset(rulerSize, y),
                    strokeWidth = if (i % 10 == 0) 2f else 1f
                )
            }

            // Cut Lines and Bleed Marks
            val dashedStroke = Stroke(
                width = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
            
            // Bleed box (Red)
            val bleedPx = bleedMm * mmToPx
            drawRect(
                color = Color.Red.copy(alpha = 0.6f),
                topLeft = Offset(rulerSize + bleedPx, rulerSize + bleedPx),
                size = Size(width - rulerSize - (bleedPx * 2), height - rulerSize - (bleedPx * 2)),
                style = dashedStroke
            )
            
            // Safe zone / inner cut line (Blue)
            val safeZonePx = 10 * mmToPx
            drawRect(
                color = Color.Blue.copy(alpha = 0.6f),
                topLeft = Offset(rulerSize + safeZonePx, rulerSize + safeZonePx),
                size = Size(width - rulerSize - (safeZonePx * 2), height - rulerSize - (safeZonePx * 2)),
                style = dashedStroke
            )
        }
    }
}
