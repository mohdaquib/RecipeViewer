package com.recipeviewer.domain.usecases

import com.recipeviewer.domain.RecipeRepository

class GetRecipeByCategoryUseCase(
    private val recipeRepository: RecipeRepository,
) {
    suspend operator fun invoke(category: String) = recipeRepository.getRecipesByCategory(category = category)
}
