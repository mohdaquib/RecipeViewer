package com.recipeviewer.ui.screens

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeviewer.di.AppModule
import com.recipeviewer.domain.RecipePreview
import com.recipeviewer.domain.error.NetworkError
import com.recipeviewer.domain.usecases.SearchRecipesUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class RecipeListViewModel(
    private val searchRecipesUseCase: SearchRecipesUseCase = AppModule.searchRecipesUseCase,
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow(RecipeListUiState())
    val uiState: StateFlow<RecipeListUiState> = _uiState

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(400L)
                .distinctUntilChanged()
                .filter { it.isNotBlank() || it.isEmpty() }
                .collectLatest { query ->
                    _uiState.update { it.copy(isLoading = true) }
                    val result =
                        if (query.isBlank()) {
                            searchRecipesUseCase("Chicken") // default recipe
                        } else {
                            searchRecipesUseCase(query)
                        }

                    _uiState.update { current ->
                        if (result.isSuccess) {
                            val recipes = result.getOrNull() ?: emptyList()
                            current.copy(
                                isLoading = false,
                                recipes = recipes,
                                errorMessage = if (recipes.isEmpty()) "No results for \"$query\"" else null,
                            )
                        } else {
                            val message =
                                when (val error = result.exceptionOrNull()) {
                                    NetworkError.NoInternet -> "No internet connection"
                                    NetworkError.Timeout -> "Connection timed out"
                                    NetworkError.UnknownHost -> "Cannot reach server. Check your connection."
                                    NetworkError.ServerError -> "Server error. Try again later."
                                    else -> error?.message ?: "Search failed"
                                }
                            current.copy(isLoading = false, errorMessage = message)
                        }
                    }
                }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }

    fun retry() {
        onSearchQueryChanged(_searchQuery.value)
    }
}

@Immutable
data class RecipeListUiState(
    val isLoading: Boolean = false,
    val recipes: List<RecipePreview> = emptyList(),
    val errorMessage: String? = null,
)
