package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BrightCoral
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.TerracottaCoral
import com.example.ui.theme.WarmAmberGold
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CoralOrbAnimation(
    modifier: Modifier = Modifier,
    sizeDp: Int = 180
) {
    val infiniteTransition = rememberInfiniteTransition(label = "coral_orb")

    // Breathing scale animation
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Rotation angle
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing)
        ),
        label = "rotation"
    )

    // Inner flare pulsation
    val innerPulse by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "inner_pulse"
    )

    Box(
        modifier = modifier.size(sizeDp.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(sizeDp.dp)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val baseRadius = (size.minDimension / 2f) * 0.72f * scale

            // Outer soft atmospheric glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        BrightCoral.copy(alpha = 0.35f * innerPulse),
                        TerracottaCoral.copy(alpha = 0.18f),
                        DeepCoral.copy(alpha = 0.05f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = baseRadius * 1.4f
                ),
                radius = baseRadius * 1.4f,
                center = center
            )

            // Orbital energy highlights
            val rads = Math.toRadians(rotation.toDouble())
            val offset1 = Offset(
                center.x + (baseRadius * 0.35f * cos(rads)).toFloat(),
                center.y + (baseRadius * 0.35f * sin(rads)).toFloat()
            )
            val offset2 = Offset(
                center.x + (baseRadius * 0.4f * cos(rads + Math.PI)).toFloat(),
                center.y + (baseRadius * 0.4f * sin(rads + Math.PI)).toFloat()
            )

            // Main Core Orb
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.95f),
                        BrightCoral.copy(alpha = 0.9f),
                        TerracottaCoral.copy(alpha = 0.85f),
                        DeepCoral
                    ),
                    center = offset1,
                    radius = baseRadius
                ),
                radius = baseRadius,
                center = center
            )

            // Dynamic warm amber secondary reflection
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        WarmAmberGold.copy(alpha = 0.65f),
                        Color.Transparent
                    ),
                    center = offset2,
                    radius = baseRadius * 0.65f
                ),
                radius = baseRadius * 0.65f,
                center = offset2
            )
        }
    }
}
