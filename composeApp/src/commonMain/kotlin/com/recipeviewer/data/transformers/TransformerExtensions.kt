package com.recipeviewer.data.transformers

import com.recipeviewer.data.remote.CategoryDto
import com.recipeviewer.data.remote.MealDetailDto
import com.recipeviewer.data.remote.MealPreviewDto
import com.recipeviewer.domain.Category
import com.recipeviewer.domain.Ingredient
import com.recipeviewer.domain.RecipeDetail
import com.recipeviewer.domain.RecipePreview

fun MealPreviewDto.toRecipePreview(): RecipePreview =
    RecipePreview(
        id = id,
        name = name,
        category = category ?: "Unknown",
        area = area,
        thumbnailUrl = thumbnailUrl,
    )

fun MealDetailDto.toRecipeDetail(): RecipeDetail {
    val ingredients = mutableListOf<Ingredient>()
    repeat(20) { index ->
        val ingredient =
            when (index + 1) {
                1 -> strIngredient1
                2 -> strIngredient2
                3 -> strIngredient3
                4 -> strIngredient4
                5 -> strIngredient5
                6 -> strIngredient6
                7 -> strIngredient7
                8 -> strIngredient8
                9 -> strIngredient9
                10 -> strIngredient10
                11 -> strIngredient11
                12 -> strIngredient12
                13 -> strIngredient13
                14 -> strIngredient14
                15 -> strIngredient15
                16 -> strIngredient16
                17 -> strIngredient17
                18 -> strIngredient18
                19 -> strIngredient19
                20 -> strIngredient20
                else -> null
            }
        val measure =
            when (index + 1) {
                1 -> strMeasure1
                2 -> strMeasure2
                3 -> strMeasure3
                4 -> strMeasure4
                5 -> strMeasure5
                6 -> strMeasure6
                7 -> strMeasure7
                8 -> strMeasure8
                9 -> strMeasure9
                10 -> strMeasure10
                11 -> strMeasure11
                12 -> strMeasure12
                13 -> strMeasure13
                14 -> strMeasure14
                15 -> strMeasure15
                16 -> strMeasure16
                17 -> strMeasure17
                18 -> strMeasure18
                19 -> strMeasure19
                20 -> strMeasure20
                else -> null
            }
        if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
            ingredients += Ingredient(name = ingredient, measure = measure)
        }
    }
    return RecipeDetail(
        id = id,
        name = name,
        category = category,
        area = area,
        instructions = instructions,
        thumbnailUrl = thumbnailUrl,
        tags = tags,
        youtubeUrl = youtubeUrl,
        sourceUrl = sourceUrl,
        ingredients = ingredients,
    )
}

fun CategoryDto.toCategory() =
    Category(
        id = name,
        name = name,
        thumbnailUrl = "https://www.themealdb.com/images/category/${name.lowercase()}.png",
        description = "",
    )
