package com.devotion.makancuy.data.datasource.category

import com.devotion.makancuy.data.source.network.model.category.CategoryResponse
import com.devotion.makancuy.data.source.network.service.RestaurantApiService

class CategoryApiDataSource(
    private val service : RestaurantApiService
) : CategoryDataSource{
    override suspend fun getCategories(): CategoryResponse {
        return service.getCategories()
    }
}