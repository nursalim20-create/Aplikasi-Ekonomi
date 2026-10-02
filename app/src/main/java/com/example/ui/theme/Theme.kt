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
    primary = SchoolBlueLight,
    onPrimary = Slate900,
    primaryContainer = SchoolNavy,
    onPrimaryContainer = SchoolBlueContainer,
    secondary = SchoolTeal,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF134E4A),
    tertiary = EcoGold,
    background = Slate900,
    surface = Slate800,
    onBackground = Slate50,
    onSurface = Slate50,
)

private val LightColorScheme = lightColorScheme(
    primary = SchoolNavy,
    onPrimary = Color.White,
    primaryContainer = SchoolBlueContainer,
    onPrimaryContainer = SchoolNavy,
    secondary = SchoolTeal,
    onSecondary = Color.White,
    secondaryContainer = SchoolTealContainer,
    tertiary = EcoGold,
    background = Slate50,
    surface = Color.White,
    onBackground = Slate900,
    onSurface = Slate900,
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Use our school brand colors by default
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
