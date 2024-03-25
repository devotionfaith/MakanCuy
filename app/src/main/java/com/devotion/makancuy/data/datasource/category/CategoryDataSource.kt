package com.devotion.makancuy.data.datasource.category

import com.devotion.makancuy.data.model.Category

interface CategoryDataSource {
    fun getCategories(): List<Category>
}