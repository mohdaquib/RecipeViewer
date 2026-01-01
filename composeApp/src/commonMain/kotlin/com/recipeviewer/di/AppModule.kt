package com.recipeviewer.di

import com.recipeviewer.data.remote.FakeRecipeApi
import com.recipeviewer.data.remote.RecipeApi
import com.recipeviewer.data.remote.RecipeApiImpl
import com.recipeviewer.data.remote.createRecipeHttpClient
import com.recipeviewer.data.repository.RecipeRepositoryImpl
import com.recipeviewer.domain.RecipeRepository

object AppModule {
    val isDebug: Boolean = true

    private val recipeApi: RecipeApi by lazy {
        if (isDebug) {
            FakeRecipeApi()
        } else {
            RecipeApiImpl(createRecipeHttpClient())
        }
    }

    val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(recipeApi = recipeApi)
    }
}