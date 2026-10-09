package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrightCoral
import com.example.ui.theme.SalonDarkCard
import com.example.ui.theme.SalonDarkEspresso
import com.example.ui.theme.TerracottaCoral
import com.example.ui.theme.WarmAmberGold

@Composable
fun KimeraHeader(
    modifier: Modifier = Modifier,
    stageSubtitle: String? = null,
    trailingAction: (@Composable () -> Unit)? = null
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = SalonDarkEspresso,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Stylized scissors / aperture emblem
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(TerracottaCoral, BrightCoral)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCut,
                            contentDescription = "Kimera Logo",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "KIMERA",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 2.sp,
                                    fontFamily = FontFamily.Serif
                                ),
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(WarmAmberGold.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "JAYANAGAR",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WarmAmberGold,
                                    letterSpacing = 1.sp
                                )
                            }
                        }

                        Text(
                            text = "AI Style Preview · Chair Consultation",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFA89F97),
                            fontSize = 11.sp
                        )
                    }
                }

                if (trailingAction != null) {
                    trailingAction()
                } else {
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SalonDarkCard)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = BrightCoral,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "PILOT V1",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrightCoral
                        )
                    }
                }
            }

            if (stageSubtitle != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stageSubtitle,
                    fontSize = 12.sp,
                    color = Color(0xFFD49339),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
