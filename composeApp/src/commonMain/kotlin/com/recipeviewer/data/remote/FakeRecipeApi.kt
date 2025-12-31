package com.recipeviewer.data.remote

import com.recipeviewer.domain.RecipeDetail
import com.recipeviewer.domain.RecipePreview
import kotlinx.coroutines.delay
import kotlin.random.Random

class FakeRecipeApi : RecipeApi {
    private val fakeDelayMs = 800L..1800L

    override suspend fun getRandomRecipe(): Result<RecipeDetail> {
        delay(fakeDelay())
        return Result.success(RecipeDetail.preview)
    }

    override suspend fun getRecipeById(id: String): Result<RecipeDetail> {
        delay(fakeDelay())
        return if (id == RecipeDetail.preview.id) {
            Result.success(RecipeDetail.preview)
        } else {
            Result.failure(NoSuchElementException("Recipe $id not found (fake)"))
        }
    }

    override suspend fun searchRecipes(query: String): Result<List<RecipePreview>> {
        delay(fakeDelay())
        return if (query.isBlank()) {
            Result.success(RecipePreview.previewItems)
        } else {
            val filtered = RecipePreview.previewItems.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true)
            }
            Result.success(filtered)
        }
    }

    override suspend fun getRecipesByCategory(category: String): Result<List<RecipePreview>> {
        delay(fakeDelay())
        val filtered = RecipePreview.previewItems.filter {
            it.category.equals(category, ignoreCase = true)
        }
        return Result.success(filtered)
    }

    private fun fakeDelay() = Random.nextLong(
        from = fakeDelayMs.first,
        until = fakeDelayMs.last + 1
    )
}