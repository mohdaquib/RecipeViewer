package com.recipeviewer.domain

data class Category(
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val description: String,
)

data class RecipePreview(
    val id: String,
    val name: String,
    val category: String,
    val area: String?,
    val thumbnailUrl: String,
)

data class Ingredient(
    val name: String,
    val measure: String,
)

data class RecipeDetail(
    val id: String,
    val name: String,
    val category: String,
    val area: String?,
    val instructions: String,
    val thumbnailUrl: String,
    val tags: String?,
    val youtubeUrl: String?,
    val sourceUrl: String?,
    val ingredients: List<Ingredient>,
)
