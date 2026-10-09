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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ui.components.KimeraPillButton
import com.example.ui.theme.BorderHairline
import com.example.ui.theme.Charcoal
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.Ivory
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.PeachTint
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.WarmCream
import com.example.ui.theme.WarmGrey

@Composable
fun RenderResultsScreen(
    clientName: String,
    referenceStyleName: String,
    variations: List<StyleVariation>,
    selectedVariationId: Int,
    barberAdvice: BarberAdvice?,
    onSelectVariation: (variationId: Int) -> Unit,
    onBack: () -> Unit,
    onTabSelected: (String) -> Unit = {}
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentVariation = variations.getOrElse(currentIndex) { variations.first() }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
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
                    text = "Previews",
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
                activeTab = "Preview",
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
                        .background(Color(0xFFE2D6CF))
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "✔ Consent", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = DeepCoral)
                Text(text = "✔ Capture", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = DeepCoral)
                Text(text = "Previews", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
                Text(text = "Outcome", fontSize = 10.sp, color = WarmGrey)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "STEP 3 OF 4 · STYLE PREVIEWS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp,
                    color = DeepCoral
                )
                Text(
                    text = "${currentIndex + 1} of ${variations.size}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Charcoal
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Pick Your Style",
                fontSize = 32.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Select a variation to review and refine with your stylist.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = WarmGrey
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Main Carousel Style Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("style_preview_carousel_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F4EE)),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Image Container
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.05f)
                            .clip(RoundedCornerShape(18.dp))
                    ) {
                        val imgRes = SalonAssetProvider.getVariationDrawableId(currentVariation.id)
                        Image(
                            painter = painterResource(id = imgRes),
                            contentDescription = currentVariation.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Top Left: Top Stylist Match badge
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color.Black.copy(alpha = 0.7f))
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = currentVariation.tag,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = RenderWhite
                            )
                        }

                        // Bottom Right: Selected badge
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(9999.dp))
                                .background(DeepCoral)
                                .padding(horizontal = 14.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "Selected",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = RenderWhite
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Title & Variation A · 1 of 3
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentVariation.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Charcoal
                        )

                        val letter = ('A'.code + currentIndex).toChar()
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(PeachTint)
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Variation $letter · ${currentIndex + 1} of ${variations.size}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepCoral
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = currentVariation.subtitle,
                        fontSize = 12.sp,
                        color = WarmGrey
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 3 Attribute Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            currentVariation.textureDetail,
                            currentVariation.fadeHeight,
                            currentVariation.productRec
                        ).forEach { spec ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(Color(0xFFF3ECE6))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = spec,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Charcoal
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Carousel dots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                variations.indices.forEach { index ->
                    val isSel = index == currentIndex
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(if (isSel) 20.dp else 8.dp, 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isSel) DeepCoral else Color(0xFFE2D6CF))
                            .clickable { currentIndex = index }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Button: Confirm Selected Look
            KimeraPillButton(
                text = "Confirm Selected Look",
                icon = Icons.Default.Check,
                onClick = { onSelectVariation(currentVariation.id) },
                backgroundColor = KimeraCoral,
                modifier = Modifier.testTag("select_variation_button")
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Regenerate with custom notes
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        currentIndex = (currentIndex + 1) % variations.size
                    }
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    tint = Charcoal,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Regenerate with custom notes",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Charcoal
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}
