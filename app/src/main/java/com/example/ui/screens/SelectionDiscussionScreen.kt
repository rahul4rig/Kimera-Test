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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.data.model.StyleVariation
import com.example.service.BarberAdvice
import com.example.ui.components.KimeraBottomNav
import com.example.ui.theme.BorderHairline
import com.example.ui.theme.Charcoal
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.PeachTint
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.WarmCream
import com.example.ui.theme.WarmGrey

@Composable
fun SelectionDiscussionScreen(
    clientName: String,
    selectedVariation: StyleVariation,
    barberName: String,
    barberAdvice: BarberAdvice?,
    initialNotes: String,
    onProceedToCut: (notes: String, fadeTweak: String, lengthTweak: String, beardTweak: String) -> Unit,
    onBack: () -> Unit,
    onTabSelected: (String) -> Unit = {}
) {
    var stylistNote by remember {
        mutableStateOf(if (initialNotes.isNotBlank() && initialNotes.contains("Fade 1cm")) initialNotes else "Fade 1cm lower on right temple")
    }

    var adjustment1 by remember { mutableStateOf(true) }
    var adjustment2 by remember { mutableStateOf(true) }
    var adjustment3 by remember { mutableStateOf(true) }

    val activeCount = listOf(adjustment1, adjustment2, adjustment3).count { it }

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

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "STEP 4 OF 4 · STYLIST ALIGNMENT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        color = DeepCoral
                    )
                    Text(
                        text = "Discussion",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Charcoal
                    )
                }

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(DeepCoral),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = RenderWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        },
        bottomBar = {
            KimeraBottomNav(
                activeTab = "Styling",
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
                        .background(DeepCoral)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(DeepCoral)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(DeepCoral)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(DeepCoral)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "✔ CONSENT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
                Text(text = "✔ CAPTURE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
                Text(text = "✔ PREVIEWS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
                Text(text = "● DISCUSSION", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Stylist Alignment",
                fontSize = 32.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Confirm final adjustments before the first cut.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = WarmGrey
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Main Confirmed Render Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("selected_render_hero_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F4EE)),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.15f)
                            .clip(RoundedCornerShape(18.dp))
                    ) {
                        val imgRes = SalonAssetProvider.getVariationDrawableId(selectedVariation.id)
                        Image(
                            painter = painterResource(id = imgRes),
                            contentDescription = selectedVariation.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = selectedVariation.title,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Charcoal
                            )
                            Text(
                                text = "Front Profile Reference",
                                fontSize = 12.sp,
                                color = WarmGrey
                            )
                        }

                        // Confirmed Pill Badge
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color(0xFFEEDCD7))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = DeepCoral,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Confirmed",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepCoral
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Stylist Adjustments Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
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
                            text = "STYLIST ADJUSTMENTS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp,
                            color = Charcoal
                        )

                        Text(
                            text = "$activeCount applied",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepCoral
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 3 Adjustments Pills
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9999.dp))
                            .background(if (adjustment1) Color(0xFFF7D5CE) else Color(0xFFEDE4DC))
                            .border(1.dp, if (adjustment1) Color(0xFFE5A89D) else BorderHairline, RoundedCornerShape(9999.dp))
                            .clickable { adjustment1 = !adjustment1 }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (adjustment1) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = DeepCoral, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        Text(text = "Slightly longer fringe", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Charcoal)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9999.dp))
                            .background(if (adjustment2) Color(0xFFF7D5CE) else Color(0xFFEDE4DC))
                            .border(1.dp, if (adjustment2) Color(0xFFE5A89D) else BorderHairline, RoundedCornerShape(9999.dp))
                            .clickable { adjustment2 = !adjustment2 }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (adjustment2) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = DeepCoral, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        Text(text = "Low skin fade (0.5)", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Charcoal)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9999.dp))
                            .background(if (adjustment3) Color(0xFFF7D5CE) else Color(0xFFEDE4DC))
                            .border(1.dp, if (adjustment3) Color(0xFFE5A89D) else BorderHairline, RoundedCornerShape(9999.dp))
                            .clickable { adjustment3 = !adjustment3 }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (adjustment3) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = DeepCoral, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        Text(text = "Natural matte texture", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Charcoal)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "STYLIST NOTE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        color = Charcoal
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = stylistNote,
                        onValueChange = { stylistNote = it },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Note",
                                tint = WarmGrey,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("barber_notes_input"),
                        shape = RoundedCornerShape(12.dp),
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
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Primary Dark Terracotta Pill Button: Ready to Cut
            Button(
                onClick = {
                    onProceedToCut(stylistNote, "Low skin fade (0.5)", "Slightly longer fringe", "Natural matte")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("lock_style_button"),
                shape = RoundedCornerShape(9999.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepCoral,
                    contentColor = RenderWhite
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ContentCut,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ready to Cut",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
