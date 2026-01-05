package com.recipeviewer.ui.screens

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeviewer.di.AppModule
import com.recipeviewer.domain.RecipePreview
import com.recipeviewer.domain.error.NetworkError
import com.recipeviewer.domain.usecases.SearchRecipesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeListViewModel(private val searchRecipesUseCase: SearchRecipesUseCase = AppModule.searchRecipesUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(RecipeListUiState())
    val uiState: StateFlow<RecipeListUiState> = _uiState

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = searchRecipesUseCase(query = "")
            _uiState.update {
                when {
                    result.isSuccess -> {
                        val recipes = result.getOrNull() ?: emptyList()
                        it.copy(isLoading = false, recipes = recipes, errorMessage = if (recipes.isEmpty()) "No recipes found" else null)
                    }
                    else -> {
                        val message = when (val error = result.exceptionOrNull()) {
                            NetworkError.NoInternet -> "No internet connection"
                            NetworkError.Timeout -> "Connection timed out"
                            NetworkError.UnknownHost -> "Cannot reach server. Check your connection."
                            NetworkError.ServerError -> "Server error. Try again later."
                            else -> error?.message ?: "Something went wrong"
                        }
                        it.copy(isLoading = false, errorMessage = message)
                    }
                }
            }
        }
    }

    fun retry() {
        loadRecipes()
    }
}

@Immutable
data class RecipeListUiState(
    val isLoading: Boolean = false,
    val recipes: List<RecipePreview> = emptyList(),
    val errorMessage: String? = null
)