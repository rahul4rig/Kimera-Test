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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.ui.theme.BorderHairline
import com.example.ui.theme.Charcoal
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.PeachTint
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.WarmCream
import com.example.ui.theme.WarmGrey

@Composable
fun OutcomeLogScreen(
    clientName: String,
    chosenStyleTitle: String,
    onSubmitOutcome: (matchStatus: String, realismRating: Int, barberAdopted: Boolean, notes: String) -> Unit,
    onBack: () -> Unit,
    onTabSelected: (String) -> Unit = {}
) {
    var matchStatus by remember { mutableStateOf("MATCHED") } // MATCHED, MINOR_DIFFERENCES, NOT_MATCHED
    var notes by remember { mutableStateOf("Client loved the texture adaptation and lower fade.") }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Charcoal
                    )
                }

                Text(
                    text = "Outcome Log",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Charcoal
                )

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF3ECE6)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = WarmGrey,
                        modifier = Modifier.size(20.dp)
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
            // Header progress step label
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "STEP 4 OF 4 · FINAL REVIEW",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp,
                    color = DeepCoral
                )
                Text(
                    text = "Outcome Check",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Charcoal
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 4 Segment Progress Track (all coral/checked)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(modifier = Modifier.weight(1f).height(4.dp).clip(RoundedCornerShape(2.dp)).background(DeepCoral))
                Box(modifier = Modifier.weight(1f).height(4.dp).clip(RoundedCornerShape(2.dp)).background(DeepCoral))
                Box(modifier = Modifier.weight(1f).height(4.dp).clip(RoundedCornerShape(2.dp)).background(DeepCoral))
                Box(modifier = Modifier.weight(1f).height(4.dp).clip(RoundedCornerShape(2.dp)).background(DeepCoral))
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "1. Consent ✓", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
                Text(text = "2. Capture ✓", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
                Text(text = "3. Previews ✓", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
                Text(text = "4. Outcome Check", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Verify Haircut Match",
                fontSize = 32.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Compare the finished cut with $clientName's chosen preview.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = WarmGrey
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Dual Comparison Staging Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F4EE)),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Left: Chosen Preview
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = RenderWhite)
                        ) {
                            Column(modifier = Modifier.padding(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                ) {
                                    Image(
                                        painter = painterResource(id = SalonAssetProvider.getVariationDrawableId(1)),
                                        contentDescription = "Chosen Preview",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = DeepCoral,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Chosen Preview",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Charcoal
                                    )
                                }
                            }
                        }

                        // Right: Finished Cut Placeholder
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = RenderWhite)
                        ) {
                            Column(modifier = Modifier.padding(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF7EBE8)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCut,
                                            contentDescription = null,
                                            tint = DeepCoral,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "CHAIR STATION 2",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Charcoal,
                                            letterSpacing = 1.sp
                                        )
                                        Text(
                                            text = "Finished Cut",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Charcoal
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Completed Cut",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Charcoal,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }

                    // Floating Swap Indicator in the middle
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(RenderWhite)
                            .border(1.dp, BorderHairline, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = null,
                            tint = Charcoal,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 3 Match Classification Options
            // Option 1: Matched Perfectly (checked)
            val isMatched = matchStatus == "MATCHED"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { matchStatus = "MATCHED" }
                    .testTag("match_chip_matched"),
                shape = RoundedCornerShape(9999.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isMatched) Color(0xFFFDE8E3) else Color(0xFFF3ECE6)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (isMatched) DeepCoral else Color(0xFFD6CBC3)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = RenderWhite,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Matched Perfectly",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Charcoal
                        )
                    }

                    Text(text = "😊", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Option 2: Close Match
            val isClose = matchStatus == "MINOR_DIFFERENCES"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { matchStatus = "MINOR_DIFFERENCES" }
                    .testTag("match_chip_minor"),
                shape = RoundedCornerShape(9999.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isClose) Color(0xFFFDE8E3) else Color(0xFFF3ECE6)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (isClose) DeepCoral else Color(0xFFD6CBC3)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = null,
                                tint = RenderWhite,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Close Match",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Charcoal
                        )
                    }

                    Text(text = "😐", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Option 3: Did Not Match
            val isUnmatched = matchStatus == "NOT_MATCHED"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { matchStatus = "NOT_MATCHED" }
                    .testTag("match_chip_unmatched"),
                shape = RoundedCornerShape(9999.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isUnmatched) Color(0xFFFDE8E3) else Color(0xFFF3ECE6)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (isUnmatched) DeepCoral else Color(0xFFD6CBC3)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = RenderWhite,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Did Not Match",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Charcoal
                        )
                    }

                    Text(text = "☹️", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Stylist & Client Notes
            Text(
                text = "Stylist & Client Notes (Optional)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3ECE6))
            ) {
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("outcome_comments_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Charcoal,
                        unfocusedTextColor = Charcoal
                    ),
                    minLines = 2
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Complete Consultation Button
            KimeraPillButton(
                text = "Complete Consultation",
                icon = Icons.Default.Check,
                onClick = {
                    val realism = if (matchStatus == "MATCHED") 5 else if (matchStatus == "MINOR_DIFFERENCES") 4 else 2
                    onSubmitOutcome(matchStatus, realism, true, notes)
                },
                backgroundColor = KimeraCoral,
                modifier = Modifier.testTag("submit_outcome_button")
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
