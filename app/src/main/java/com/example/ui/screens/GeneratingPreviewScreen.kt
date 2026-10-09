package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.KimeraWordmark
import com.example.ui.theme.BorderHairline
import com.example.ui.theme.Charcoal
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.PeachTint
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.WarmCream
import com.example.ui.theme.WarmGrey
import com.example.ui.viewmodel.RenderProgressState

@Composable
fun GeneratingPreviewScreen(
    clientName: String,
    referenceStyleName: String,
    progressState: RenderProgressState
) {
    val infiniteTransition = rememberInfiniteTransition(label = "generating_orb")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 18.dp, bottom = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                KimeraWordmark(fontSize = 22)
            }
        },
        containerColor = WarmCream
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crafting Your Previews",
                    fontSize = 32.sp,
                    lineHeight = 42.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = Charcoal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Infusing natural lighting and fade depth tailored to your contours.",
                    fontSize = 14.sp,
                    color = WarmGrey,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    lineHeight = 22.sp
                )
            }

            // Center: Ambient Blur Glow with Floating Generating Circle
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .testTag("coral_orb_container"),
                contentAlignment = Alignment.Center
            ) {
                // Background Soft Radial Glow
                Canvas(modifier = Modifier.size(280.dp)) {
                    val center = Offset(size.width / 2f, size.height / 2f)
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                SoftPeach.copy(alpha = 0.85f * pulseScale),
                                KimeraCoral.copy(alpha = 0.35f),
                                Color.Transparent
                            ),
                            center = center,
                            radius = (size.minDimension / 2f) * pulseScale
                        ),
                        radius = (size.minDimension / 2f) * pulseScale,
                        center = center
                    )
                }

                // Inner Circular Card
                Box(
                    modifier = Modifier
                        .size(190.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFDECE8))
                        .border(1.dp, Color(0xFFF5D5CD), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Magic wand / shears badge
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(RenderWhite.copy(alpha = 0.85f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = DeepCoral,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Generating 3\nPreviews",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Charcoal,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "BESPOKE TEXTURES",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarmGrey,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            // Bottom Section: Adapting Chip, Stopwatch, Progress bar, Kimera Footer
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Adapting Chip
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9999.dp))
                        .background(Color(0xFFF3ECE6))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(KimeraCoral)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Adapting fade & texture to face structure...",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Charcoal
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Timer Pill: 00:35 / Under 60s
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9999.dp))
                        .background(Color(0xFFF9F4EE))
                        .border(1.dp, BorderHairline, RoundedCornerShape(9999.dp))
                        .padding(horizontal = 14.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = DeepCoral,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "00:${progressState.elapsedSeconds.toString().padStart(2, '0')}  /  Under 60s",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Charcoal
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Progress Bar
                LinearProgressIndicator(
                    progress = { progressState.progressPercent },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = KimeraCoral,
                    trackColor = Color(0xFFE5DDD5)
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Kimera Jayanagar Footer
                KimeraWordmark(fontSize = 20)
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Bengaluru · Jayanagar 4th Block",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = WarmGrey
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
