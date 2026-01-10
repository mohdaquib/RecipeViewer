package com.recipeviewer.domain.usecases

import com.recipeviewer.domain.RecipeRepository

class GetRecipeCategoriesUseCase(
    private val recipeRepository: RecipeRepository,
) {
    suspend operator fun invoke() = recipeRepository.getCategories()
}
