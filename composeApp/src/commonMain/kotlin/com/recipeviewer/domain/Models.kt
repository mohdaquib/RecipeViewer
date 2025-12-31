package com.recipeviewer.domain

data class Category(
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val description: String
)

data class RecipePreview(
    val id: String,
    val name: String,
    val category: String,
    val area: String?,
    val thumbnailUrl: String
) {
    companion object {
        val previewItems = listOf(
            RecipePreview(
                id = "52772",
                name = "Teriyaki Chicken Casserole",
                category = "Chicken",
                area = "Japanese",
                thumbnailUrl = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg"
            ),
            RecipePreview(
                id = "52804",
                name = "Smoked Haddock Kedgeree",
                category = "Seafood",
                area = "British",
                thumbnailUrl = "https://www.themealdb.com/images/media/meals/15544v1585568052.jpg"
            ),
            RecipePreview(
                id = "52910",
                name = "Shawarma",
                category = "Miscellaneous",
                area = "Egyptian",
                thumbnailUrl = "https://www.themealdb.com/images/media/meals/kxr8t91570474015.jpg"
            )
        )
    }
}

data class Ingredient(
    val name: String,
    val measure: String
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
    val ingredients: List<Ingredient>
) {
    companion object {
        val preview = RecipeDetail(
            id = "52772",
            name = "Teriyaki Chicken Casserole",
            category = "Chicken",
            area = "Japanese",
            instructions = "Preheat oven to 350° F. Spray a 9x13-inch baking pan with non-stick spray.\n" +
                    "Combine chicken, soy sauce, ½ cup water, brown sugar, honey, garlic, ginger, cornstarch, and red pepper flakes in a large bowl. Mix well.\n" +
                    "...",
            thumbnailUrl = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
            tags = "Meat,Casserole",
            youtubeUrl = "https://www.youtube.com/watch?v=4aZr5hZXP_s",
            sourceUrl = null,
            ingredients = listOf(
                Ingredient("Chicken", "1 ½ lbs"),
                Ingredient("Soy Sauce", "⅓ cup"),
                Ingredient("Water", "½ cup"),
                Ingredient("Brown Sugar", "½ cup"),
                Ingredient("Honey", "¼ cup"),
                Ingredient("Garlic", "2 cloves minced"),
                Ingredient("Ginger", "1 tsp ground"),
                Ingredient("Cornstarch", "2 tbsp"),
                Ingredient("Red Pepper Flakes", "¼ tsp"),
                Ingredient("Rice", "3 cups cooked")
            )
        )
    }
}