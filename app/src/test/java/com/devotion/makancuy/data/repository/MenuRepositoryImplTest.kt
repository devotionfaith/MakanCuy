package com.devotion.makancuy.data.repository

import app.cash.turbine.test
import com.devotion.makancuy.data.datasource.menu.MenuDataSource
import com.devotion.makancuy.data.model.Cart
import com.devotion.makancuy.data.source.network.model.checkout.CheckoutResponse
import com.devotion.makancuy.data.source.network.model.menu.MenuItemResponse
import com.devotion.makancuy.data.source.network.model.menu.MenuResponse
import com.devotion.makancuy.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {
    @MockK
    lateinit var ds: MenuDataSource

    private lateinit var repo: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = MenuRepositoryImpl(ds)
    }

    @Test
    fun getMenu_success() {
        val menu1 =
            MenuItemResponse(
                alamatResto = "asasa",
                detail = "hahahasasasaaha",
                harga = 9000,
                hargaFormat = "sasda",
                imageUrl = "adafsa",
                nama = "dfdsfe",
            )
        val menu2 =
            MenuItemResponse(
                alamatResto = "mamamam",
                detail = "hahahaha",
                harga = 9000,
                hargaFormat = "sembilan ribu",
                imageUrl = "heheheh",
                nama = "string",
            )
        val mockList = listOf(menu1, menu2)
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockList
        runTest {
            coEvery { ds.getMenu(any()) } returns mockResponse
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { ds.getMenu(any()) }
            }
        }
    }

    @Test
    fun getMenu_loading() {
        val menu1 =
            MenuItemResponse(
                alamatResto = "asasa",
                detail = "hahahasasasaaha",
                harga = 9000,
                hargaFormat = "sasda",
                imageUrl = "adafsa",
                nama = "dfdsfe",
            )
        val menu2 =
            MenuItemResponse(
                alamatResto = "mamamam",
                detail = "hahahaha",
                harga = 9000,
                hargaFormat = "sembilan ribu",
                imageUrl = "heheheh",
                nama = "string",
            )
        val mockList = listOf(menu1, menu2)
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockList
        runTest {
            coEvery { ds.getMenu(any()) } returns mockResponse
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { ds.getMenu(any()) }
            }
        }
    }

    @Test
    fun getMenu_error() {
        runTest {
            coEvery { ds.getMenu(any()) } throws IllegalStateException("mock error")
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { ds.getMenu(any()) }
            }
        }
    }

    @Test
    fun getMenu_empty() {
        val mockList = listOf<MenuItemResponse>()
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockList
        runTest {
            coEvery { ds.getMenu(any()) } returns mockResponse
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { ds.getMenu(any()) }
            }
        }
    }

    @Test
    fun createOrder_success() {
        val mockCart = listOf<Cart>()
        val mockUsername = "gustiiii"
        val mockResponse =
            CheckoutResponse(
                code = 200,
                message = "success",
                status = true,
            )
        runTest {
            coEvery { ds.createOrder(any()) } returns mockResponse
            repo.createOrder(mockCart, mockUsername).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { ds.createOrder(any()) }
            }
        }
    }

    @Test
    fun createOrder_failed() {
        val mockCart = listOf<Cart>()
        val mockUsername = "gustiiii"
        val mockResponse =
            CheckoutResponse(
                code = 100,
                message = "failed",
                status = false,
            )
        runTest {
            coEvery { ds.createOrder(any()) } returns mockResponse
            repo.createOrder(mockCart, mockUsername).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { ds.createOrder(any()) }
            }
        }
    }
}
