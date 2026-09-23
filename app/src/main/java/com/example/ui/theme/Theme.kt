package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = TealPrimaryDark,
    onPrimary = Color(0xFF003831),
    primaryContainer = Color(0xFF005147),
    onPrimaryContainer = Color(0xFF70F7E0),
    secondary = CyanAccentLight,
    onSecondary = Color(0xFF00363D),
    secondaryContainer = Color(0xFF004F58),
    onSecondaryContainer = Color(0xFFBCEBF2),
    tertiary = StatusHealthyDark,
    onTertiary = Color(0xFF003920),
    background = SlateNavyDark,
    onBackground = TextPrimaryDark,
    surface = SlateNavyCardDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SlateNavyCardElevated,
    onSurfaceVariant = TextSecondaryDark,
    outline = SlateBorderDark,
    error = StatusDetectedDark,
    onError = Color(0xFF4C0014)
)

private val LightColorScheme = lightColorScheme(
    primary = TealPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB2ECE4),
    onPrimaryContainer = Color(0xFF00201B),
    secondary = CyanAccent,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCFFAFE),
    onSecondaryContainer = Color(0xFF002025),
    tertiary = StatusHealthy,
    onTertiary = Color.White,
    background = SlateNavyLight,
    onBackground = TextPrimaryLight,
    surface = Color.White,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = TextSecondaryLight,
    outline = SlateBorder,
    error = StatusDetected,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep medical identity crisp and consistent
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
