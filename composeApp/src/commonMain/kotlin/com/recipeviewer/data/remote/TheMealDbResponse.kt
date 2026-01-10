package com.recipeviewer.data.remote

import com.recipeviewer.domain.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealsResponse(
    @SerialName("meals")
    val meals: List<MealPreviewDto>? = null,
)

@Serializable
data class MealDetailResponse(
    @SerialName("meals")
    val meals: List<MealDetailDto>? = null,
)

@Serializable
data class MealDetailDto(
    @SerialName("idMeal")
    val id: String,
    @SerialName("strMeal")
    val name: String,
    @SerialName("strCategory")
    val category: String,
    @SerialName("strArea")
    val area: String?,
    @SerialName("strInstructions")
    val instructions: String,
    @SerialName("strMealThumb")
    val thumbnailUrl: String,
    @SerialName("strTags")
    val tags: String?,
    @SerialName("strYoutube")
    val youtubeUrl: String?,
    @SerialName("strSource")
    val sourceUrl: String?,
    // Ingredients & measures
    @SerialName("strIngredient1") val strIngredient1: String?,
    @SerialName("strIngredient2") val strIngredient2: String?,
    @SerialName("strIngredient3") val strIngredient3: String?,
    @SerialName("strIngredient4") val strIngredient4: String?,
    @SerialName("strIngredient5") val strIngredient5: String?,
    @SerialName("strIngredient6") val strIngredient6: String?,
    @SerialName("strIngredient7") val strIngredient7: String?,
    @SerialName("strIngredient8") val strIngredient8: String?,
    @SerialName("strIngredient9") val strIngredient9: String?,
    @SerialName("strIngredient10") val strIngredient10: String?,
    @SerialName("strIngredient11") val strIngredient11: String?,
    @SerialName("strIngredient12") val strIngredient12: String?,
    @SerialName("strIngredient13") val strIngredient13: String?,
    @SerialName("strIngredient14") val strIngredient14: String?,
    @SerialName("strIngredient15") val strIngredient15: String?,
    @SerialName("strIngredient16") val strIngredient16: String?,
    @SerialName("strIngredient17") val strIngredient17: String?,
    @SerialName("strIngredient18") val strIngredient18: String?,
    @SerialName("strIngredient19") val strIngredient19: String?,
    @SerialName("strIngredient20") val strIngredient20: String?,
    @SerialName("strMeasure1") val strMeasure1: String?,
    @SerialName("strMeasure2") val strMeasure2: String?,
    @SerialName("strMeasure3") val strMeasure3: String?,
    @SerialName("strMeasure4") val strMeasure4: String?,
    @SerialName("strMeasure5") val strMeasure5: String?,
    @SerialName("strMeasure6") val strMeasure6: String?,
    @SerialName("strMeasure7") val strMeasure7: String?,
    @SerialName("strMeasure8") val strMeasure8: String?,
    @SerialName("strMeasure9") val strMeasure9: String?,
    @SerialName("strMeasure10") val strMeasure10: String?,
    @SerialName("strMeasure11") val strMeasure11: String?,
    @SerialName("strMeasure12") val strMeasure12: String?,
    @SerialName("strMeasure13") val strMeasure13: String?,
    @SerialName("strMeasure14") val strMeasure14: String?,
    @SerialName("strMeasure15") val strMeasure15: String?,
    @SerialName("strMeasure16") val strMeasure16: String?,
    @SerialName("strMeasure17") val strMeasure17: String?,
    @SerialName("strMeasure18") val strMeasure18: String?,
    @SerialName("strMeasure19") val strMeasure19: String?,
    @SerialName("strMeasure20") val strMeasure20: String?,
)

@Serializable
data class MealPreviewDto(
    @SerialName("idMeal") val id: String,
    @SerialName("strMeal") val name: String,
    @SerialName("strCategory") val category: String? = null,
    @SerialName("strArea") val area: String? = null,
    @SerialName("strMealThumb") val thumbnailUrl: String
)

@Serializable
data class CategoriesResponse(
    @SerialName("meals")
    val meals: List<CategoryDto>?,
)

@Serializable
data class CategoryDto(
    @SerialName("strCategory") val name: String,
)
