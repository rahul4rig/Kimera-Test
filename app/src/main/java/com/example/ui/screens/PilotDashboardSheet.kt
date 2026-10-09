package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.data.model.ChairSession
import com.example.ui.theme.BorderHairline
import com.example.ui.theme.Charcoal
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.Ivory
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.PeachTint
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarmCream
import com.example.ui.theme.WarmGrey
import com.example.ui.viewmodel.PilotMetrics
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PilotDashboardSheet(
    sessions: List<ChairSession>,
    metrics: PilotMetrics,
    onDismiss: () -> Unit,
    onSelectSession: (ChairSession) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var filterPricing by remember { mutableStateOf("ALL") }

    val filteredSessions = remember(sessions, filterPricing) {
        if (filterPricing == "ALL") sessions
        else sessions.filter { it.pricingModel == filterPricing }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = WarmCream,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Sheet Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pilot Analytics & Sessions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Charcoal
                    )
                    Text(
                        text = "Kimera Jayanagar Cohort 1 & 2 Log",
                        fontSize = 12.sp,
                        color = WarmGrey
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("close_pilot_dashboard")
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Charcoal)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pricing Model Filter Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filters = listOf(
                    Pair("ALL", "All (${sessions.size})"),
                    Pair("fee_100", "₹100 Fee Model"),
                    Pair("commission_2pct", "2% Service Model")
                )
                filters.forEach { (key, label) ->
                    val isSel = filterPricing == key
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(9999.dp))
                            .background(if (isSel) PeachTint else Ivory)
                            .clickable { filterPricing = key }
                            .padding(vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSel) DeepCoral else Charcoal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Sessions List
            Text(
                text = "Recorded Chair Sessions (${filteredSessions.size})",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Charcoal
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredSessions) { s ->
                    val dateFormat = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
                    val formattedDate = dateFormat.format(Date(s.createdAt))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelectSession(s)
                                onDismiss()
                            },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = RenderWhite),
                        border = BorderStroke(1.dp, BorderHairline)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = s.clientName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Charcoal
                                )

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(9999.dp))
                                        .background(PeachTint)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = if (s.pricingModel == "fee_100") "₹100 Fee" else "2% Cut",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepCoral
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Style: ${s.selectedVariationTitle} · ${s.barberName}",
                                fontSize = 12.sp,
                                color = WarmGrey
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = formattedDate,
                                    fontSize = 11.sp,
                                    color = Color(0xFFA59B94)
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (s.outcomeMatchStatus != null) {
                                        Text(
                                            text = s.outcomeMatchStatus.replace("_", " "),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (s.outcomeMatchStatus == "MATCHED") SuccessGreen else DeepCoral
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }

                                    Row {
                                        (1..s.outcomeRealismRating).forEach { _ ->
                                            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = DeepCoral, modifier = Modifier.size(11.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
