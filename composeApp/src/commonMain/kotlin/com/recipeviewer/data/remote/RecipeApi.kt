package com.recipeviewer.data.remote

import com.recipeviewer.data.transformers.toRecipeDetail
import com.recipeviewer.data.transformers.toRecipePreview
import com.recipeviewer.domain.RecipeDetail
import com.recipeviewer.domain.RecipePreview
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

interface RecipeApi {
    suspend fun getRandomRecipe(): Result<RecipeDetail>
    suspend fun getRecipeById(id: String): Result<RecipeDetail>
    suspend fun searchRecipes(query: String): Result<List<RecipePreview>>
    suspend fun getRecipesByCategory(category: String): Result<List<RecipePreview>>
}

class RecipeApiImpl(private val client: HttpClient) : RecipeApi {
    private val baseUrl = "https://www.themealdb.com/api/json/v1/1"

    override suspend fun getRandomRecipe(): Result<RecipeDetail> = safeApiCall {
        val response: MealResponse = client.get("$baseUrl/random.php").body()
        response.meals?.firstOrNull()
            ?.toRecipeDetail()
            ?: throw NoSuchElementException("No random recipe found")
    }

    override suspend fun getRecipeById(id: String): Result<RecipeDetail> = safeApiCall {
        val response: MealResponse = client.get("$baseUrl/lookup.php") {
            parameter("i", id)
        }.body()

        response.meals?.firstOrNull()
            ?.toRecipeDetail()
            ?: throw NoSuchElementException("Recipe with id $id not found")
    }

    override suspend fun searchRecipes(query: String): Result<List<RecipePreview>> = safeApiCall {
        val response: MealsResponse = client.get("$baseUrl/search.php") {
            parameter("s", query)
        }.body()

        response.meals
            ?.map { it.toRecipePreview() }
            ?: emptyList()
    }

    override suspend fun getRecipesByCategory(category: String): Result<List<RecipePreview>> =
        safeApiCall {
            val response: MealsResponse = client.get("$baseUrl/filter.php") {
                parameter("c", category)
            }.body()

            response.meals
                ?.map { it.toRecipePreview() }
                ?: emptyList()
        }

    private suspend fun <T> safeApiCall(block: suspend () -> T): Result<T> =
        withContext(Dispatchers.IO) {
            try {
                Result.success(block())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}