package com.recipeviewer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme =
    lightColorScheme(
        primary =
            androidx.compose.ui.graphics
                .Color(0xFF6750A4),
        secondary =
            androidx.compose.ui.graphics
                .Color(0xFF625B71),
        tertiary =
            androidx.compose.ui.graphics
                .Color(0xFF7D5260),
        background =
            androidx.compose.ui.graphics
                .Color(0xFFFFFBFE),
        surface =
            androidx.compose.ui.graphics
                .Color(0xFFFFFBFE),
        onPrimary = androidx.compose.ui.graphics.Color.White,
        onSecondary = androidx.compose.ui.graphics.Color.White,
        onTertiary = androidx.compose.ui.graphics.Color.White,
        onBackground =
            androidx.compose.ui.graphics
                .Color(0xFF1C1B1F),
        onSurface =
            androidx.compose.ui.graphics
                .Color(0xFF1C1B1F),
    )

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColorScheme, typography = androidx.compose.material3.Typography(), content = content)
}
