package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderHairline
import com.example.ui.theme.Charcoal
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.Ivory
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.WarmCream
import com.example.ui.theme.WarmGrey
import com.example.ui.theme.WarmSand

@Composable
fun KimeraWordmark(
    modifier: Modifier = Modifier,
    color: Color = Charcoal,
    fontSize: Int = 22
) {
    Text(
        text = "kimera",
        fontSize = fontSize.sp,
        lineHeight = (fontSize + 6).sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = 1.sp,
        color = color,
        modifier = modifier
    )
}

@Composable
fun KimeraStepProgress(
    currentStep: Int, // 1 to 4
    modifier: Modifier = Modifier,
    labels: List<String> = listOf("1. Consent", "2. Capture", "3. Previews", "4. Outcome")
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (1..4).forEach { step ->
                val isCompletedOrCurrent = step <= currentStep
                val barColor = if (isCompletedOrCurrent) KimeraCoral else Color(0xFFE5DDD5)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(barColor)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            labels.forEachIndexed { index, label ->
                val step = index + 1
                val isCurrent = step == currentStep
                val isPast = step < currentStep
                val textColor = when {
                    isCurrent -> DeepCoral
                    isPast -> KimeraCoral
                    else -> Color(0xFFA59B94)
                }
                val check = if (isPast) "✓" else ""

                Text(
                    text = if (check.isNotEmpty()) "$label $check" else label,
                    fontSize = 10.sp,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                    color = textColor
                )
            }
        }
    }
}

@Composable
fun KimeraBottomNav(
    activeTab: String, // "Session", "Preview", "Styling", "Journal"
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = WarmCream,
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderHairline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tabs = listOf(
                Pair("Session", Icons.Default.Chair),
                Pair("Preview", Icons.Default.Face),
                Pair("Styling", Icons.Default.Palette),
                Pair("Journal", Icons.AutoMirrored.Filled.MenuBook)
            )

            tabs.forEach { (title, icon) ->
                val isSelected = activeTab == title
                val itemColor = if (isSelected) DeepCoral else WarmGrey

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onTabSelected(title) }
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                        .testTag("nav_tab_${title.lowercase()}")
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = itemColor,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = itemColor
                    )
                }
            }
        }
    }
}

@Composable
fun KimeraPillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = Icons.AutoMirrored.Filled.ArrowForward,
    backgroundColor: Color = KimeraCoral,
    contentColor: Color = RenderWhite,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(9999.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = Color(0xFFE2D6CF),
            disabledContentColor = Color(0xFFA19790)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            if (icon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
