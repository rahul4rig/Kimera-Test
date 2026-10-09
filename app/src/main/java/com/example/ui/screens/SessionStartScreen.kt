package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SalonAssetProvider
import com.example.ui.components.KimeraBottomNav
import com.example.ui.components.KimeraPillButton
import com.example.ui.components.KimeraWordmark
import com.example.ui.theme.BorderHairline
import com.example.ui.theme.Charcoal
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.Ivory
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.PeachTint
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.WarmCream
import com.example.ui.theme.WarmGrey
import com.example.ui.theme.WarmSand

@Composable
fun SessionStartScreen(
    onStartSession: (name: String, phone: String, barber: String, pricingModel: String) -> Unit,
    onOpenPilotDashboard: () -> Unit,
    onTabSelected: (String) -> Unit = {}
) {
    var clientName by remember { mutableStateOf("Rohan V.") }
    var selectedBarber by remember { mutableStateOf("Vikram") }
    val stylists = listOf("Vikram", "Anand", "Sameer")

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                KimeraWordmark(fontSize = 24)

                // Chair 03 badge with coral dot
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9999.dp))
                        .background(Ivory)
                        .border(1.dp, BorderHairline, RoundedCornerShape(9999.dp))
                        .clickable { onOpenPilotDashboard() }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Chair 03",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Charcoal
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(KimeraCoral)
                    )
                }
            }
        },
        bottomBar = {
            KimeraBottomNav(
                activeTab = "Session",
                onTabSelected = onTabSelected
            )
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
            // Big Serif Headline
            Text(
                text = "New Consultation",
                fontSize = 32.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Start a chair-side hairstyle preview",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = WarmGrey
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Suite North Photo Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2.1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Ivory)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = SalonAssetProvider.salonSuiteInteriorResId),
                        contentDescription = "Salon Suite",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Suite North · Ready pill overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp)
                            .clip(RoundedCornerShape(9999.dp))
                            .background(Color.Black.copy(alpha = 0.55f))
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "Suite North · Ready",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = RenderWhite
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Client Name Input
            Text(
                text = "Client Name",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = clientName,
                onValueChange = { clientName = it },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = WarmGrey,
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("client_name_input"),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = KimeraCoral,
                    unfocusedBorderColor = BorderHairline,
                    focusedContainerColor = RenderWhite,
                    unfocusedContainerColor = RenderWhite,
                    focusedTextColor = Charcoal,
                    unfocusedTextColor = Charcoal
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Stylist Selector
            Text(
                text = "Stylist",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                stylists.forEach { stylist ->
                    val isSelected = selectedBarber == stylist
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(9999.dp))
                            .background(if (isSelected) PeachTint else Ivory)
                            .border(
                                1.dp,
                                if (isSelected) KimeraCoral else BorderHairline,
                                RoundedCornerShape(9999.dp)
                            )
                            .clickable { selectedBarber = stylist }
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(DeepCoral)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        Text(
                            text = stylist,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) DeepCoral else Charcoal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Consultation Process 4 Steps Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F4EE)),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CONSULTATION PROCESS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp,
                            color = Charcoal
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(PeachTint)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "4 Steps",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepCoral
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 4 progress step tracks
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // 01 Consent (active)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(KimeraCoral)
                        )
                        // 02
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0xFFE2D6CF))
                        )
                        // 03
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0xFFE2D6CF))
                        )
                        // 04
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
                        Text(text = "01 Consent", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Charcoal)
                        Text(text = "02 Capture", fontSize = 11.sp, color = WarmGrey)
                        Text(text = "03 Previews", fontSize = 11.sp, color = WarmGrey)
                        Text(text = "04 Cut & Check", fontSize = 11.sp, color = WarmGrey)
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Start Session Button
            KimeraPillButton(
                text = "Start Session",
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = {
                    onStartSession(clientName, "+91 98450 12345", selectedBarber, "fee_100")
                },
                modifier = Modifier.testTag("start_session_button")
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
