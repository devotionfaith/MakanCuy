package com.devotion.makancuy.data.repository

import com.devotion.makancuy.data.datasource.category.CategoryDataSource
import com.devotion.makancuy.data.mapper.toCategories
import com.devotion.makancuy.data.model.Category
import com.devotion.makancuy.utils.ResultWrapper
import com.devotion.makancuy.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(
    private val dataSource: CategoryDataSource,
) : CategoryRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow { dataSource.getCategories().data.toCategories() }
    }
}
