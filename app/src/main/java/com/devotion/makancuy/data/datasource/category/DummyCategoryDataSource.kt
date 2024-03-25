package com.devotion.makancuy.data.datasource.category

import com.devotion.makancuy.data.model.Category

class DummyCategoryDataSource: CategoryDataSource{
    override fun getCategories(): List<Category> {
        return listOf(
            Category(image = "drawable.ic_cat_rice", name = "Nasi"),
            Category(image = "drawable.ic_cat_noodles", name = "Mie"),
            Category(image = "drawable.ic_cat_snack", name = "Snack"),
            Category(image = "drawable.ic_cat_beverages", name = "Drink"),
            Category(image = "drawable.ic_cat_pastry", name = "Pastry"),
            Category(image = "drawable.ic_cat_dessert", name = "Dessert")
        )
    }
}