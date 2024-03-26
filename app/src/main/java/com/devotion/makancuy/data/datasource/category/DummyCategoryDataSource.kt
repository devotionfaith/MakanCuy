package com.devotion.makancuy.data.datasource.category

import com.devotion.makancuy.data.model.Category

class DummyCategoryDataSource: CategoryDataSource{
    override fun getCategories(): List<Category> {
        return listOf(
            Category(imageurl = "https://github.com/devotionfaith/makancuy-assets/blob/main/img-categories/ic_cat_rice.jpeg?raw=true", name = "Nasi"),
            Category(imageurl = "https://github.com/devotionfaith/makancuy-assets/blob/main/img-categories/ic_cat_noodles.jpeg?raw=true", name = "Mie"),
            Category(imageurl = "https://github.com/devotionfaith/makancuy-assets/blob/main/img-categories/ic_cat_snack.jpeg?raw=true", name = "Snack"),
            Category(imageurl = "https://github.com/devotionfaith/makancuy-assets/blob/main/img-categories/ic_cat_beverages.jpeg?raw=true", name = "Drink"),
            Category(imageurl = "https://github.com/devotionfaith/makancuy-assets/blob/main/img-categories/ic_cat_pastry.jpeg?raw=true", name = "Pastry"),
            Category(imageurl = "https://github.com/devotionfaith/makancuy-assets/blob/main/img-categories/ic_cat_dessert.jpeg?raw=true", name = "Dessert")
        )
    }
}