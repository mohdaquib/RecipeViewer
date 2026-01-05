package com.recipeviewer.ui.screens

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeviewer.di.AppModule
import com.recipeviewer.domain.RecipeDetail
import com.recipeviewer.domain.error.NetworkError
import com.recipeviewer.domain.usecases.GetRecipeByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val recipeId: String,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase = AppModule.getRecipeByIdUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: Flow<RecipeDetailUiState> = _uiState

    init {
        loadRecipe()
    }

    private fun loadRecipe() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = getRecipeByIdUseCase(recipeId)
            _uiState.update {
                when {
                    result.isSuccess -> {
                        val recipe = result.getOrNull()
                        if (recipe != null) {
                            it.copy(isLoading = false, recipe = recipe, errorMessage = null)
                        } else {
                            it.copy(isLoading = false, recipe = null, errorMessage = "Recipe not found")
                        }
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
        loadRecipe()
    }
}

@Immutable
data class RecipeDetailUiState(
    val isLoading: Boolean = false,
    val recipe: RecipeDetail? = null,
    val errorMessage: String? = null
)
