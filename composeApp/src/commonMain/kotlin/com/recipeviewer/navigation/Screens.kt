package com.recipeviewer.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.recipeviewer.ui.screens.RecipeDetailScreen
import com.recipeviewer.ui.screens.RecipeListScreen

class RecipeListScreenRoute : cafe.adriel.voyager.core.screen.Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        RecipeListScreen(onRecipeClick = { recipeId ->
            navigator.push(RecipeDetailScreenRoute(recipeId))
        })
    }
}

class RecipeDetailScreenRoute(
    private val recipeId: String,
) : cafe.adriel.voyager.core.screen.Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        RecipeDetailScreen(recipeId = recipeId, onBackClick = {
            navigator.pop()
        })
    }
}
