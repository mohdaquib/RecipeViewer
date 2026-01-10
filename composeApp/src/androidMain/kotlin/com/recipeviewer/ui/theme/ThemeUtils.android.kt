package com.recipeviewer.ui.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual val supportsDynamicTheming: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@Composable
actual fun getDynamicColorScheme(darkTheme: Boolean?): ColorScheme? {
    if (!supportsDynamicTheming) return null

    val context = LocalContext.current
    return if (darkTheme == true) {
        dynamicDarkColorScheme(context)
    } else {
        dynamicLightColorScheme(context)
    }
}
