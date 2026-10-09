package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SalonAssetProvider
import com.example.data.model.ChairSession
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.KimeraBottomNav
import com.example.ui.components.KimeraPillButton
import com.example.ui.components.KimeraWordmark
import com.example.ui.theme.BorderHairline
import com.example.ui.theme.Charcoal
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.PeachTint
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarmCream
import com.example.ui.theme.WarmGrey
import com.example.ui.viewmodel.PilotMetrics
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SessionCompleteScreen(
    session: ChairSession,
    pilotMetrics: PilotMetrics,
    onStartNewSession: () -> Unit,
    onOpenPilotDashboard: () -> Unit,
    onTabSelected: (String) -> Unit = {}
) {
    var showConfetti by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                KimeraWordmark(fontSize = 24)

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9999.dp))
                        .background(PeachTint)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Session Complete",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepCoral
                    )
                }
            }
        },
        bottomBar = {
            KimeraBottomNav(
                activeTab = "Journal",
                onTabSelected = onTabSelected
            )
        },
        containerColor = WarmCream
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                // Completion Banner
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF9F4EE))
                        .border(1.dp, BorderHairline, RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8F5E9)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = SuccessGreen,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Consultation Finalized",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Charcoal
                    )
                    Text(
                        text = "${session.clientName} · Stylist: ${session.barberName}",
                        fontSize = 12.sp,
                        color = WarmGrey
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Session Summary Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("session_summary_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = RenderWhite),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Styling Blueprint Summary",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Charcoal
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Render thumbnail
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            Image(
                                painter = painterResource(id = SalonAssetProvider.getVariationDrawableId(session.selectedVariationId)),
                                contentDescription = "Finished Cut Preview",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1.3f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = session.selectedVariationTitle,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Charcoal
                            )
                            Text(
                                text = "Target: ${session.referenceStyleName}",
                                fontSize = 12.sp,
                                color = WarmGrey
                            )

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(PeachTint)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = session.outcomeMatchStatus?.replace("_", " ") ?: "MATCHED",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepCoral
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                (1..5).forEach { s ->
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = if (s <= session.outcomeRealismRating) DeepCoral else Color(0xFFD6CBC3),
                                        modifier = Modifier.size(13.dp)
                                    )
                                }
                            }
                        }
                    }

                    if (session.barberNotes.isNotBlank()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFF3ECE6))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Stylist Directive: ${session.barberNotes}",
                                fontSize = 12.sp,
                                color = Charcoal,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Pilot KPI Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F4EE)),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Pilot Success Metrics (21 Days)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Charcoal
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PilotMetricItem(
                        label = "Preview Accuracy",
                        target = "66%+",
                        current = "${pilotMetrics.matchRatePercent}%"
                    )
                    PilotMetricItem(
                        label = "Barber Tool Adoption",
                        target = "70%+",
                        current = "${pilotMetrics.barberAdoptionPercent}%"
                    )
                    PilotMetricItem(
                        label = "Client Trust (Consent)",
                        target = "80%+",
                        current = "${pilotMetrics.consentRatePercent}%"
                    )
                    PilotMetricItem(
                        label = "Render Speed (< 120s)",
                        target = "8/10",
                        current = "${pilotMetrics.speedCompliancePercent}%"
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Primary Start Next Session CTA
            KimeraPillButton(
                text = "Start Next Chair Session",
                icon = Icons.Default.RestartAlt,
                onClick = {
                    showConfetti = true
                    scope.launch {
                        delay(1500)
                        onStartNewSession()
                    }
                },
                modifier = Modifier.testTag("new_session_button")
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onOpenPilotDashboard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("open_pilot_history_button"),
                shape = RoundedCornerShape(9999.dp),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Icon(imageVector = Icons.Default.BarChart, contentDescription = null, tint = Charcoal, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "View Pilot History & Analytics", color = Charcoal, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Full-screen celebratory Confetti Explosion
        ConfettiOverlay(
            isTriggered = showConfetti,
            onAnimationEnd = { showConfetti = false }
        )
    }
}
}

@Composable
private fun PilotMetricItem(label: String, target: String, current: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label (Target: $target)", fontSize = 12.sp, color = WarmGrey)
        Text(text = current, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
    }
}
