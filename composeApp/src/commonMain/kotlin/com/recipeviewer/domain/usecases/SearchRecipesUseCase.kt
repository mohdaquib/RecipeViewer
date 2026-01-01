package com.recipeviewer.domain.usecases

import com.recipeviewer.domain.RecipeRepository

class SearchRecipesUseCase(
    private val recipeRepository: RecipeRepository,
) {
    suspend operator fun invoke(query: String) = recipeRepository.searchRecipes(query = query)
}
