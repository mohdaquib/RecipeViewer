package com.recipeviewer.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

expect val supportsDynamicTheming: Boolean

@Composable
expect fun getDynamicColorScheme(darkTheme: Boolean?): ColorScheme?