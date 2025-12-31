package com.recipeviewer.navigation

sealed interface Screen {
    data object RecipeList : Screen
    data class RecipeDetail(val id: String) : Screen
}