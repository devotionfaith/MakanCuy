package com.devotion.makancuy.data.datasource.category

import com.devotion.makancuy.data.source.network.model.category.CategoryResponse
import com.devotion.makancuy.data.source.network.service.RestaurantApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CategoryApiDataSourceTest {
    @MockK
    lateinit var service: RestaurantApiService

    private lateinit var dataSource: CategoryApiDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = CategoryApiDataSource(service)
    }

    @Test
    fun getCategories() {
        runTest {
            val mockResponse = mockk<CategoryResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse
            val actualResult = dataSource.getCategories()
            coVerify { service.getCategories() }
            assertEquals(mockResponse, actualResult)
        }
    }
}
