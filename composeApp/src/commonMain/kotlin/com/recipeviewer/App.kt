package com.recipeviewer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.recipeviewer.navigation.Screen
import com.recipeviewer.ui.screens.RecipeDetailScreen
import com.recipeviewer.ui.screens.RecipeListScreen
import com.recipeviewer.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        val navController = remember { mutableStateOf<Screen>(Screen.RecipeList) }

        when (val screen = navController.value) {
            is Screen.RecipeList -> {
                RecipeListScreen(onRecipeClick = { id ->
                    navController.value = Screen.RecipeDetail(id = id)
                })
            }

            is Screen.RecipeDetail -> {
                RecipeDetailScreen(
                    recipeId = screen.id,
                    onBackClick = {
                        navController.value = Screen.RecipeList
                    },
                )
            }
        }
    }
}
