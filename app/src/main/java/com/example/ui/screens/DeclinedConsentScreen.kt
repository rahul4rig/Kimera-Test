package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.ui.theme.WarmCream
import com.example.ui.theme.WarmGrey

@Composable
fun DeclinedConsentScreen(
    clientName: String,
    onRestartAiSession: () -> Unit,
    onCompleteConsultation: () -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onRestartAiSession) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Charcoal
                    )
                }
                KimeraWordmark(fontSize = 24)
            }
        },
        containerColor = WarmCream
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(PeachTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChatBubbleOutline,
                    contentDescription = null,
                    tint = DeepCoral,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Standard Consultation Active",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Charcoal
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Proceeding with conventional verbal consultation for $clientName. No camera capture or AI processing will take place.",
                fontSize = 14.sp,
                color = WarmGrey,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = RenderWhite),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Pilot Protocol Note",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = DeepCoral
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "This consultation is logged as a voluntary client opt-out and is kept separate from tool performance metrics.",
                        fontSize = 12.sp,
                        color = Charcoal
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onCompleteConsultation,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("finish_standard_consultation_button"),
                shape = RoundedCornerShape(9999.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KimeraCoral)
            ) {
                Icon(imageVector = Icons.Default.ContentCut, contentDescription = null, tint = RenderWhite, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Proceed with Haircut", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = RenderWhite)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onRestartAiSession,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(9999.dp),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Text(text = "Change Mind — Enable AI Style Preview", color = DeepCoral, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
