package com.devotion.makancuy.data.repository

import com.devotion.makancuy.data.datasource.category.CategoryDataSource
import com.devotion.makancuy.data.model.Category

interface CategoryRepository {
    fun getCategories(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategories()

}