package com.recipeviewer

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.recipeviewer.navigation.Screen
import com.recipeviewer.ui.screens.RecipeListScreen
import com.recipeviewer.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        val currentScreen = remember { mutableStateOf<Screen>(Screen.RecipeList) }
        when(val screen = currentScreen.value) {
            is Screen.RecipeList -> {
                RecipeListScreen(onRecipeClick = { id ->
                    currentScreen.value = Screen.RecipeDetail(id)
                })
            }

            is Screen.RecipeDetail -> {
                // TODO: implement later
                Text(text = "Recipe Detail screen - ID: ${screen.id}", modifier = Modifier)
            }
        }
    }
}