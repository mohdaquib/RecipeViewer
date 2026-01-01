package com.recipeviewer.domain.usecases

import com.recipeviewer.domain.RecipeRepository

class GetRandomRecipeUseCase(
    private val recipeRepository: RecipeRepository,
) {
    suspend operator fun invoke() = recipeRepository.getRandomRecipe()
}
