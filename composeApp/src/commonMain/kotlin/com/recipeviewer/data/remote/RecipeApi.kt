package com.recipeviewer.data.remote

import com.recipeviewer.data.transformers.toRecipeDetail
import com.recipeviewer.data.transformers.toRecipePreview
import com.recipeviewer.domain.RecipeDetail
import com.recipeviewer.domain.RecipePreview
import com.recipeviewer.domain.error.AppError
import com.recipeviewer.domain.error.NetworkError
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

class RecipeApiImpl(
    private val client: HttpClient,
) : RecipeApi {
    private val baseUrl = "https://www.themealdb.com/api/json/v1/1"

    override suspend fun getRandomRecipe(): Result<RecipeDetail> =
        safeApiCall {
            val response: MealResponse = client.get("$baseUrl/random.php").body()
            response.meals
                ?.firstOrNull()
                ?.toRecipeDetail()
                ?: throw NoSuchElementException("No random recipe found")
        }

    override suspend fun getRecipeById(id: String): Result<RecipeDetail> =
        safeApiCall {
            val response: MealResponse =
                client
                    .get("$baseUrl/lookup.php") {
                        parameter("i", id)
                    }.body()

            response.meals
                ?.firstOrNull()
                ?.toRecipeDetail()
                ?: throw NoSuchElementException("Recipe with id $id not found")
        }

    override suspend fun searchRecipes(query: String): Result<List<RecipePreview>> =
        safeApiCall {
            val response: MealsResponse =
                client
                    .get("$baseUrl/search.php") {
                        parameter("s", query)
                    }.body()

            response.meals
                ?.map { it.toRecipePreview() }
                ?: emptyList()
        }

    override suspend fun getRecipesByCategory(category: String): Result<List<RecipePreview>> =
        safeApiCall {
            val response: MealsResponse =
                client
                    .get("$baseUrl/filter.php") {
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
                val error =
                    when {
                        e.message?.contains("Unable to resolve host", ignoreCase = true) == true ||
                            e.message?.contains("Network is unreachable", ignoreCase = true) == true ||
                            e.message?.contains("No route to host", ignoreCase = true) == true -> {
                            NetworkError.NoInternet
                        }

                        // Timeout
                        e.message?.contains("timeout", ignoreCase = true) == true -> {
                            NetworkError.Timeout
                        }

                        // 404 / not found
                        e.message?.contains("404", ignoreCase = true) == true -> {
                            NetworkError.ServerError
                        }

                        // DNS / host resolution (covers UnknownHostException equivalent)
                        e.message?.contains("unknown host", ignoreCase = true) == true ||
                            e.message?.contains("hostname", ignoreCase = true) == true -> {
                            NetworkError.UnknownHost
                        }

                        else -> {
                            AppError.Unknown(e.message ?: "Unknown error", e)
                        }
                    }
                Result.failure(error as Throwable)
            }
        }
}
