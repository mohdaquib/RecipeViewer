package com.recipeviewer.domain.usecases

import com.recipeviewer.domain.RecipeRepository

class GetRecipeByIdUseCase(
    private val recipeRepository: RecipeRepository,
) {
    suspend operator fun invoke(id: String) = recipeRepository.getRecipeById(id = id)
}
