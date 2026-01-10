package com.recipeviewer.di

import com.recipeviewer.data.remote.RecipeApi
import com.recipeviewer.data.remote.RecipeApiImpl
import com.recipeviewer.data.remote.createRecipeHttpClient
import com.recipeviewer.data.repository.RecipeRepositoryImpl
import com.recipeviewer.domain.RecipeRepository
import com.recipeviewer.domain.usecases.GetRecipeByCategoryUseCase
import com.recipeviewer.domain.usecases.GetRecipeByIdUseCase
import com.recipeviewer.domain.usecases.GetRecipeCategoriesUseCase
import com.recipeviewer.domain.usecases.SearchRecipesUseCase

object AppModule {
    private val recipeApi: RecipeApi by lazy {
        RecipeApiImpl(createRecipeHttpClient())
    }

    val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(recipeApi = recipeApi)
    }

    val searchRecipesUseCase: SearchRecipesUseCase by lazy {
        SearchRecipesUseCase(recipeRepository = recipeRepository)
    }

    val getRecipeByIdUseCase: GetRecipeByIdUseCase by lazy {
        GetRecipeByIdUseCase(recipeRepository = recipeRepository)
    }

    val getRecipeCategoriesUseCase: GetRecipeCategoriesUseCase by lazy {
        GetRecipeCategoriesUseCase(recipeRepository = recipeRepository)
    }

    val getRecipeByCategoryUseCase: GetRecipeByCategoryUseCase by lazy {
        GetRecipeByCategoryUseCase(recipeRepository = recipeRepository)
    }
}