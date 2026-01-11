package com.recipeviewer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.recipeviewer.domain.Category
import com.recipeviewer.domain.RecipePreview
import com.recipeviewer.ui.components.RecipeImage
import com.recipeviewer.ui.components.SearchableTopAppBar
import com.recipeviewer.ui.components.ShimmerRecipeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    onRecipeClick: (String) -> Unit,
    viewModel: RecipeListViewModel = remember { RecipeListViewModel() },
) {
    val state by viewModel.uiState.collectAsState()
    val isRefreshing = state.isLoading && !state.recipes.isEmpty()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            Column {
                SearchableTopAppBar(
                    title = "Recipes 🍳",
                    searchQuery = searchQuery,
                    onSearchQueryChange = viewModel::onSearchQueryChanged,
                    onClearSearch = viewModel::clearSearch,
                )
                CategoryChips(
                    categories = viewModel.categories.value,
                    selectedCategory = viewModel.selectedCategory.value,
                    onCategorySelected = viewModel::selectCategory,
                )
            }
        },
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.retry() },
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(0.dp),
        ) {
            when {
                state.isLoading && state.recipes.isEmpty() -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(10) {
                            ShimmerRecipeCard()
                        }
                    }
                }

                state.errorMessage != null -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                state.errorMessage ?: "Something went wrong",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = { viewModel.retry() }) {
                                Text("Retry")
                            }
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding =
                            PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp,
                            ),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(state.recipes) { recipe ->
                            RecipeCard(
                                recipe = recipe,
                                onClick = { onRecipeClick(recipe.id) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecipeCard(
    recipe: RecipePreview,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .minimumInteractiveComponentSize()
                .clickable(onClick = onClick)
                .semantics(mergeDescendants = true) {
                    role = Role.Button
                    contentDescription =
                        buildString {
                            append(recipe.name)
                            append(", ${recipe.category}")
                            if (recipe.area != null) append(" from ${recipe.area}")
                        }
                },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            RecipeImage(
                url = recipe.thumbnailUrl,
                contentDescription = recipe.name,
                modifier =
                    Modifier
                        .size(100.dp)
                        .aspectRatio(1f),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "${recipe.category} • ${recipe.area ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
fun CategoryChips(
    categories: List<Category>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All") },
                leadingIcon = {
                    if (selectedCategory == null) Icon(Icons.Default.Check, null)
                },
            )
        }

        items(categories) { category ->
            FilterChip(
                selected = selectedCategory == category.name,
                onClick = { onCategorySelected(category.name) },
                label = { Text(category.name) },
                leadingIcon = {
                    if (selectedCategory == category.name) Icon(Icons.Default.Check, null)
                },
            )
        }
    }
}
