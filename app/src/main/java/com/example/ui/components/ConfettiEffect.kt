package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private enum class ConfettiShape {
    RECTANGLE,
    RIBBON,
    CIRCLE
}

private data class Particle(
    val originXFraction: Float,
    val originYFraction: Float,
    val angleRad: Float,
    val initialSpeed: Float,
    val color: Color,
    val width: Float,
    val height: Float,
    val shape: ConfettiShape,
    val rotationSpeed: Float,
    val wobbleFreq: Float,
    val wobbleSpeed: Float
)

@Composable
fun ConfettiOverlay(
    isTriggered: Boolean,
    modifier: Modifier = Modifier,
    onAnimationEnd: () -> Unit = {}
) {
    if (!isTriggered) return

    val progress = remember { Animatable(0f) }

    val confettiColors = listOf(
        KimeraCoral,
        DeepCoral,
        WarningAmber,
        SoftPeach,
        SuccessGreen,
        Color(0xFFFFB4AA),
        Color(0xFFFFE082),
        RenderWhite
    )

    val particles = remember {
        val random = Random(System.currentTimeMillis())
        List(110) {
            val angle = random.nextFloat() * (Math.PI.toFloat()) + (Math.PI.toFloat()) // upwards burst
            val speed = random.nextFloat() * 1400f + 600f
            val shape = when (random.nextInt(3)) {
                0 -> ConfettiShape.RECTANGLE
                1 -> ConfettiShape.RIBBON
                else -> ConfettiShape.CIRCLE
            }
            Particle(
                originXFraction = random.nextFloat() * 0.4f + 0.3f, // around center bottom
                originYFraction = 0.72f, // near button height
                angleRad = angle,
                initialSpeed = speed,
                color = confettiColors[random.nextInt(confettiColors.size)],
                width = if (shape == ConfettiShape.RIBBON) (random.nextFloat() * 8f + 6f) else (random.nextFloat() * 12f + 10f),
                height = if (shape == ConfettiShape.RIBBON) (random.nextFloat() * 22f + 16f) else (random.nextFloat() * 12f + 10f),
                shape = shape,
                rotationSpeed = (random.nextFloat() - 0.5f) * 1200f,
                wobbleFreq = random.nextFloat() * 15f + 5f,
                wobbleSpeed = random.nextFloat() * 8f + 4f
            )
        }
    }

    LaunchedEffect(isTriggered) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
        )
        onAnimationEnd()
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val t = progress.value
        if (t >= 1f) return@Canvas

        val gravity = 2200f // downward acceleration
        val alpha = if (t > 0.65f) (1f - (t - 0.65f) / 0.35f).coerceIn(0f, 1f) else 1f

        particles.forEach { p ->
            val originX = size.width * p.originXFraction
            val originY = size.height * p.originYFraction

            val vx = p.initialSpeed * cos(p.angleRad)
            val vy = p.initialSpeed * sin(p.angleRad)

            // Kinematics with air drag and sway
            val curX = originX + vx * t + sin(t * p.wobbleFreq) * 45f
            val curY = originY + vy * t + 0.5f * gravity * t * t

            val currentRotation = p.rotationSpeed * t

            rotate(degrees = currentRotation, pivot = Offset(curX, curY)) {
                when (p.shape) {
                    ConfettiShape.CIRCLE -> {
                        drawCircle(
                            color = p.color.copy(alpha = alpha),
                            radius = p.width / 2f,
                            center = Offset(curX, curY)
                        )
                    }
                    ConfettiShape.RECTANGLE, ConfettiShape.RIBBON -> {
                        drawRect(
                            color = p.color.copy(alpha = alpha),
                            topLeft = Offset(curX - p.width / 2f, curY - p.height / 2f),
                            size = Size(p.width, p.height)
                        )
                    }
                }
            }
        }
    }
}
