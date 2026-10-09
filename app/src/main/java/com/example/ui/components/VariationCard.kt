package com.example.ui.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SalonAssetProvider
import com.example.data.model.StyleVariation
import com.example.ui.theme.BrightCoral
import com.example.ui.theme.SalonDarkBorder
import com.example.ui.theme.SalonDarkCard
import com.example.ui.theme.SalonDarkCardElevated
import com.example.ui.theme.TerracottaCoral
import com.example.ui.theme.WarmAmberGold

@Composable
fun VariationCard(
    variation: StyleVariation,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onCompareToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) BrightCoral else SalonDarkBorder
    val borderWidth = if (isSelected) 2.dp else 1.dp
    val imageRes = SalonAssetProvider.getVariationDrawableId(variation.id)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("variation_card_${variation.id}")
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) SalonDarkCardElevated else SalonDarkCard
        ),
        border = BorderStroke(borderWidth, borderColor)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Image with tag overlay and selected check
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.1f)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = variation.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )

                // Subtle gradient overlay at bottom of image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                            )
                        )
                )

                // Tag badge top-left
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black.copy(alpha = 0.75f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = variation.tag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrightCoral
                    )
                }

                // Selected badge top-right
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(BrightCoral),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title and Subtitle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = variation.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = variation.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFA89F97),
                        fontSize = 11.sp
                    )
                }

                Text(
                    text = "Var #${variation.id}",
                    style = MaterialTheme.typography.labelSmall,
                    color = WarmAmberGold,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = variation.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFD7CEC7),
                fontSize = 12.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Technical Specs chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF231E1B))
                        .border(1.dp, SalonDarkBorder, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = TerracottaCoral,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = variation.fadeHeight,
                            fontSize = 10.sp,
                            color = Color(0xFFFAF7F2)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF231E1B))
                        .border(1.dp, SalonDarkBorder, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Top: ${variation.topLength}",
                        fontSize = 10.sp,
                        color = Color(0xFFFAF7F2)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Comparison button
            OutlinedButton(
                onClick = onCompareToggle,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("compare_button_${variation.id}"),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, SalonDarkBorder)
            ) {
                Icon(
                    imageVector = Icons.Default.Compare,
                    contentDescription = null,
                    tint = BrightCoral,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Compare vs Reference",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}
