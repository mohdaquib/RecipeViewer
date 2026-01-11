package com.recipeviewer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity

private val LightColorScheme =
    lightColorScheme(
        primary = Color(0xFF6750A4),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFEADDFF),
        onPrimaryContainer = Color(0xFF21005D),
        secondary = Color(0xFF625B71),
        tertiary = Color(0xFF7D5260),
        background = Color(0xFFFFFBFE),
        surface = Color(0xFFFFFBFE),
        onSecondary = Color.White,
        onTertiary = Color.White,
        onBackground = Color(0xFF1C1B1F),
        onSurface = Color(0xFF1C1B1F),
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = Color(0xFFD0BCFF),
        onPrimary = Color(0xFF381E72),
        primaryContainer = Color(0xFF4F378B),
        onPrimaryContainer = Color(0xFFEADDFF),
        secondary = Color(0xFF625B71),
        tertiary = Color(0xFF7D5260),
        background = Color(0xFFFFFBFE),
        surface = Color(0xFFFFFBFE),
        onSecondary = Color.White,
        onTertiary = Color.White,
        onBackground = Color(0xFF1C1B1F),
        onSurface = Color(0xFF1C1B1F),
    )

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val typography =
        Typography().copy(
            bodyLarge =
                Typography().bodyLarge.copy(
                    fontSize = Typography().bodyLarge.fontSize * LocalDensity.current.fontScale,
                ),
        )
    val colorScheme =
        when {
            supportsDynamicTheming -> getDynamicColorScheme(darkTheme)
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        } ?: (if (darkTheme) DarkColorScheme else LightColorScheme)
    MaterialTheme(colorScheme = colorScheme, typography = typography, content = content)
}
