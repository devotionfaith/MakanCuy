package com.devotion.makancuy.data.datasource.menu

import com.devotion.makancuy.data.source.network.model.checkout.CheckoutRequestPayload
import com.devotion.makancuy.data.source.network.model.checkout.CheckoutResponse
import com.devotion.makancuy.data.source.network.model.menu.MenuResponse
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

class MenuApiDataSourceTest {
    @MockK
    lateinit var service: RestaurantApiService

    private lateinit var dataSource: MenuDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = MenuApiDataSource(service)
    }

    @Test
    fun getMenu() {
        runTest {
            val mockResponse = mockk<MenuResponse>(relaxed = true)
            coEvery { service.getMenu(any()) } returns mockResponse
            val actualResult = dataSource.getMenu("makanan")
            coVerify { service.getMenu(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockCheckoutRequestPayload = mockk<CheckoutRequestPayload>(relaxed = true)
            val mockCheckoutResponse = mockk<CheckoutResponse>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockCheckoutResponse
            val actualResult = dataSource.createOrder(mockCheckoutRequestPayload)
            coVerify { service.createOrder(any()) }
            assertEquals(mockCheckoutResponse, actualResult)
        }
    }
}
