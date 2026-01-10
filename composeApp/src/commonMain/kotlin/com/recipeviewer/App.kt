package com.recipeviewer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.recipeviewer.navigation.RecipeListScreenRoute
import com.recipeviewer.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme(darkTheme = isSystemInDarkTheme()) {
        Navigator(screen = RecipeListScreenRoute()) { navigator ->
            SlideTransition(navigator)
        }
    }
}
