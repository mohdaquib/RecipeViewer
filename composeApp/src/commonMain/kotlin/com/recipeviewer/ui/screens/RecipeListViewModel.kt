package com.recipeviewer.ui.screens

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeviewer.di.AppModule
import com.recipeviewer.domain.Category
import com.recipeviewer.domain.RecipePreview
import com.recipeviewer.domain.error.NoInternet
import com.recipeviewer.domain.error.ServerError
import com.recipeviewer.domain.error.Timeout
import com.recipeviewer.domain.error.UnknownHost
import com.recipeviewer.domain.usecases.GetRecipeByCategoryUseCase
import com.recipeviewer.domain.usecases.GetRecipeCategoriesUseCase
import com.recipeviewer.domain.usecases.SearchRecipesUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class RecipeListViewModel(
    private val searchRecipesUseCase: SearchRecipesUseCase = AppModule.searchRecipesUseCase,
    private val getRecipeCategoriesUseCase: GetRecipeCategoriesUseCase = AppModule.getRecipeCategoriesUseCase,
    private val getRecipeByCategoryUseCase: GetRecipeByCategoryUseCase = AppModule.getRecipeByCategoryUseCase,
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _uiState = MutableStateFlow(RecipeListUiState())
    val uiState: StateFlow<RecipeListUiState> = _uiState

    init {
        loadCategories()
        observeFilters()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val result = getRecipeCategoriesUseCase()
            if (result.isSuccess) {
                _categories.value = result.getOrNull() ?: emptyList()
            }
        }
    }

    private fun observeFilters() {
        viewModelScope.launch {
            combine(searchQuery.debounce(400L).distinctUntilChanged(), selectedCategory) { query, category ->
                Pair(query, category)
            }.collectLatest { (query, category) ->
                _uiState.update { it.copy(isLoading = true) }
                val result =
                    when {
                        category != null && query.isBlank() -> {
                            getRecipeByCategoryUseCase(category)
                        }

                        category == null && query.isBlank() -> {
                            searchRecipesUseCase(query = "Chicken")
                        }

                        else -> {
                            searchRecipesUseCase(query)
                        }
                    }

                _uiState.update { current ->
                    if (result.isSuccess) {
                        val recipes = result.getOrNull() ?: emptyList()
                        current.copy(
                            isLoading = false,
                            recipes = recipes,
                            errorMessage =
                                if (recipes.isEmpty()) {
                                    when {
                                        query.isNotBlank() -> "No results for \"$query\""
                                        category != null -> "No recipes in $category"
                                        else -> "No recipes found"
                                    }
                                } else {
                                    null
                                },
                        )
                    } else {
                        val message =
                            when (val error = result.exceptionOrNull()) {
                                NoInternet() -> "No internet connection"
                                Timeout() -> "Connection timed out"
                                UnknownHost() -> "Cannot reach server. Check your connection."
                                ServerError() -> "Server error. Try again later."
                                else -> error?.message ?: "Something went wrong"
                            }
                        current.copy(isLoading = false, errorMessage = message)
                    }
                }
            }
        }
    }

    fun selectCategory(category: String?) {
        _selectedCategory.value = category
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
