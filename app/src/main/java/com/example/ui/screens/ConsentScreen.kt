package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.KimeraPillButton
import com.example.ui.components.KimeraWordmark
import com.example.ui.theme.BorderHairline
import com.example.ui.theme.Charcoal
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.PeachTint
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.ToggleGreen
import com.example.ui.theme.WarmCream
import com.example.ui.theme.WarmGrey

@Composable
fun ConsentScreen(
    clientName: String,
    onConfirmConsent: (photoConsent: Boolean, retentionOptIn: Boolean) -> Unit,
    onDeclineConsent: () -> Unit,
    onBack: () -> Unit
) {
    var cameraConsent by remember { mutableStateOf(true) }
    var saveNextVisit by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Charcoal
                    )
                }

                KimeraWordmark(
                    modifier = Modifier.padding(start = 12.dp),
                    fontSize = 24
                )
            }
        },
        containerColor = WarmCream
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
            // 4 Segment Progress Track
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(KimeraCoral)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFFE2D6CF))
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFFE2D6CF))
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFFE2D6CF))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "1. CONSENT", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
                Text(text = "2. CAPTURE", fontSize = 11.sp, color = Color(0xFFA59B94))
                Text(text = "3. PREVIEWS", fontSize = 11.sp, color = Color(0xFFA59B94))
                Text(text = "4. OUTCOME", fontSize = 11.sp, color = Color(0xFFA59B94))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Subtitle tag
            Text(
                text = "PRIVACY & PERMISSIONS",
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp,
                color = DeepCoral
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Serif Headline
            Text(
                text = "Photo & Privacy",
                fontSize = 32.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Charcoal
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "We need a quick photo to adapt styles to your face shape.",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = WarmGrey
            )

            Spacer(modifier = Modifier.height(26.dp))

            // Card 1: Camera & AI Preview (Required)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = RenderWhite),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Camera & AI Preview",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Charcoal
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(PeachTint)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Required",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepCoral
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Used only during this consultation to preview hairstyles.",
                            fontSize = 12.sp,
                            color = WarmGrey,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Switch(
                        checked = cameraConsent,
                        onCheckedChange = { cameraConsent = it },
                        modifier = Modifier.testTag("consent_photo_switch"),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = RenderWhite,
                            checkedTrackColor = ToggleGreen,
                            checkedBorderColor = Color.Transparent,
                            uncheckedThumbColor = Color(0xFFA19790),
                            uncheckedTrackColor = Color(0xFFE2D6CF),
                            uncheckedBorderColor = Color.Transparent
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card 2: Save for Next Visit (Optional)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = RenderWhite),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Save for Next Visit",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Charcoal
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(Color(0xFFF3ECE6))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Optional",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = WarmGrey
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Keep your adapted blueprint on file.",
                            fontSize = 12.sp,
                            color = WarmGrey,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Switch(
                        checked = saveNextVisit,
                        onCheckedChange = { saveNextVisit = it },
                        modifier = Modifier.testTag("consent_retention_switch"),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = RenderWhite,
                            checkedTrackColor = ToggleGreen,
                            checkedBorderColor = Color.Transparent,
                            uncheckedThumbColor = RenderWhite,
                            uncheckedTrackColor = Color(0xFFE2D6CF),
                            uncheckedBorderColor = Color.Transparent
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Primary Button: Continue to Photo ->
            KimeraPillButton(
                text = "Continue to Photo",
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = {
                    onConfirmConsent(cameraConsent, saveNextVisit)
                },
                enabled = cameraConsent,
                modifier = Modifier.testTag("confirm_consent_button")
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Skip AI Preview (Verbal Consultation)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDeclineConsent() }
                    .padding(vertical = 8.dp)
                    .testTag("decline_consent_button"),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ChatBubbleOutline,
                    contentDescription = null,
                    tint = Charcoal,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Skip AI Preview (Verbal Consultation)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Charcoal
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
