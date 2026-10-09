package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SalonColorScheme = lightColorScheme(
    primary = KimeraCoral,
    onPrimary = RenderWhite,
    primaryContainer = SoftPeach,
    onPrimaryContainer = DeepCoral,
    secondary = DeepCoral,
    onSecondary = RenderWhite,
    secondaryContainer = Ivory,
    onSecondaryContainer = Charcoal,
    background = WarmCream,
    onBackground = Charcoal,
    surface = Ivory,
    onSurface = Charcoal,
    surfaceVariant = RenderWhite,
    onSurfaceVariant = WarmGrey,
    outline = WarmSand,
    outlineVariant = DividerMuted
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SalonColorScheme,
        typography = Typography,
        content = content
    )
}
