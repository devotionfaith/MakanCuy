package com.devotion.makancuy.data.datasource.category

import com.devotion.makancuy.data.source.network.model.category.CategoryResponse

interface CategoryDataSource {
    suspend fun getCategories(): CategoryResponse
}
