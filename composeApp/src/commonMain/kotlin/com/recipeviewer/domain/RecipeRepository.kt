package com.recipeviewer.domain

interface RecipeRepository {
    suspend fun getRandomRecipe(): Result<RecipeDetail>
    suspend fun getRecipeById(id: String): Result<RecipeDetail>
    suspend fun searchRecipes(query: String): Result<List<RecipePreview>>
    suspend fun getRecipesByCategory(category: String): Result<List<RecipePreview>>
}