package com.devotion.makancuy.data.repository

import app.cash.turbine.test
import com.devotion.makancuy.data.datasource.cart.CartDataSource
import com.devotion.makancuy.data.model.Cart
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.data.model.PriceItem
import com.devotion.makancuy.data.source.local.database.entity.CartEntity
import com.devotion.makancuy.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {
    @MockK
    lateinit var ds: CartDataSource
    private lateinit var repo: CartRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = CartRepositoryImpl(ds)
    }

    @Test
    fun getUserCartData_success() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 8000.0,
                menuPriceFormatted = "Rp. 8.000",
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        val entity2 =
            CartEntity(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 8000.0,
                menuPriceFormatted = "Rp. 8.000",
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.first?.size)
                assertEquals(48000.0, actualData.payload?.second)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getUserCartData_loading() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 8000.0,
                menuPriceFormatted = "Rp. 8.000",
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        val entity2 =
            CartEntity(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 8000.0,
                menuPriceFormatted = "Rp. 8.000",
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getUserCartData_error() {
        every { ds.getAllCarts() } returns
            flow {
                throw IllegalStateException("mock error")
            }
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2211)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getUserCartData_empty() {
        val mockList = listOf<CartEntity>()
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutData_success() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 8000.0,
                menuPriceFormatted = "Rp. 8.000",
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        val entity2 =
            CartEntity(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 8000.0,
                menuPriceFormatted = "Rp. 8.000",
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        val mockList = listOf(entity1, entity2)
        val mockPriceList = listOf(PriceItem("awfawf", 24000.0), PriceItem("awfawf", 24000.0))
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.first?.size)
                assertEquals(mockPriceList, actualData.payload?.second)
                assertEquals(48000.0, actualData.payload?.third)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutData_error() {
        every { ds.getAllCarts() } returns
            flow {
                throw IllegalStateException("Mock Error")
            }
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2211)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutData_loading() {
        val mockList = listOf<CartEntity>()
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(1100)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutData_empty() {
        val mockList = listOf<CartEntity>()
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                assertEquals(true, actualData.payload?.first?.isEmpty())
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun createCart_success() {
        val mockProduct = mockk<Menu>(relaxed = true)
        every { mockProduct.id } returns "123"
        coEvery { ds.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockProduct, 3, "afawfawf")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2201)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Success)
                    assertEquals(true, actualData.payload)
                    coVerify { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun createCart_with_null_note() {
        val mockProduct = mockk<Menu>(relaxed = true)
        every { mockProduct.id } returns "123"
        coEvery { ds.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockProduct, 3)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2201)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Success)
                    assertEquals(true, actualData.payload)
                    coVerify { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun createCart_loading() {
        val mockProduct = mockk<Menu>(relaxed = true)
        every { mockProduct.id } returns "123"
        coEvery { ds.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockProduct, 3, "aowkwk")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(101)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Loading)
                    coVerify { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun createCart_error_processing() {
        val mockCart = mockk<Menu>(relaxed = true)
        every { mockCart.id } returns "123"
        coEvery { ds.insertCart(any()) } throws IllegalStateException("Mock Error")
        runTest {
            repo.createCart(mockCart, 3, "aowkwk")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(201)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Error)
                    coVerify { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun createCart_error_no_product_id() {
        val mockCart = mockk<Menu>(relaxed = true)
        every { mockCart.id } returns null
        coEvery { ds.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockCart, 3, "aowkwk")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2001)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Error)
                    coVerify(atLeast = 0) { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun decreaseCart_when_quantity_more_than_0() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 10000.0,
                menuPriceFormatted = "Rp. 10.000",
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        coEvery { ds.deleteCart(any()) } returns 1
        coEvery { ds.updateCart(any()) } returns 1
        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 0) { ds.deleteCart(any()) }
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun decreaseCart_when_quantity_less_than_1() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 10000.0,
                menuPriceFormatted = "Rp. 10.000",
                itemQuantity = 1,
                itemNotes = "awfwafawfawfafwafa",
            )
        coEvery { ds.deleteCart(any()) } returns 1
        coEvery { ds.updateCart(any()) } returns 1
        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 0) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun increaseCart() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 10000.0,
                menuPriceFormatted = "Rp. 10.000",
                itemQuantity = 1,
                itemNotes = "awfwafawfawfafwafa",
            )
        coEvery { ds.updateCart(any()) } returns 1
        runTest {
            repo.increaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun setCartNotes() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 10000.0,
                menuPriceFormatted = "Rp. 10.000",
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        coEvery { ds.updateCart(any()) } returns 1
        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun deleteCart() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 10000.0,
                menuPriceFormatted = "Rp. 10.000",
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        coEvery { ds.deleteCart(any()) } returns 1
        runTest {
            repo.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ds.deleteCart(any()) }
            }
        }
    }

    @Test
    fun deleteAll() {
        coEvery { ds.deleteAll() } returns Unit
        runTest {
            val result =
                repo.deleteAll().map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Success)
                }
            coVerify { ds.deleteAll() }
            assertEquals(Unit, result)
        }
    }
}
