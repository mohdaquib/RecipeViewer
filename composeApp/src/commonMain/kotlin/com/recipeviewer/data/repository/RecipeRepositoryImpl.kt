package com.recipeviewer.data.repository

import com.recipeviewer.data.remote.RecipeApi
import com.recipeviewer.domain.Category
import com.recipeviewer.domain.RecipeDetail
import com.recipeviewer.domain.RecipePreview
import com.recipeviewer.domain.RecipeRepository

class RecipeRepositoryImpl(
    private val recipeApi: RecipeApi,
) : RecipeRepository {
    override suspend fun getRandomRecipe(): Result<RecipeDetail> = recipeApi.getRandomRecipe()

    override suspend fun getRecipeById(id: String): Result<RecipeDetail> = recipeApi.getRecipeById(id = id)

    override suspend fun searchRecipes(query: String): Result<List<RecipePreview>> = recipeApi.searchRecipes(query = query)

    override suspend fun getRecipesByCategory(category: String): Result<List<RecipePreview>> =
        recipeApi.getRecipesByCategory(category = category)

    override suspend fun getCategories(): Result<List<Category>> = recipeApi.getCategories()
}
