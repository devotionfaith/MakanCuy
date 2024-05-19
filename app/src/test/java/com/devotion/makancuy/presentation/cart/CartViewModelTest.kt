package com.devotion.makancuy.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devotion.makancuy.data.repository.CartRepository
import com.devotion.makancuy.data.repository.UserRepository
import com.devotion.makancuy.tools.MainCoroutineRule
import com.devotion.makancuy.tools.getOrAwaitValue
import com.devotion.makancuy.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val courotineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cr: CartRepository

    @MockK
    lateinit var ur: UserRepository

    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(CartViewModel(cr, ur))
    }

    @Test
    fun getCartList() {
        every { cr.getUserCartData() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Pair(listOf(mockk(relaxed = true), mockk(relaxed = true)), 8000.0),
                    ),
                )
            }
        val result = viewModel.getCartList().getOrAwaitValue()
        assertEquals(2, result.payload?.first?.size)
        assertEquals(8000.0, result.payload?.second)
    }

    @Test
    fun isLogin() {
        every { ur.isLoggedIn() } returns true
        val result = viewModel.isLogin()
        verify { viewModel.isLogin() }
        assertTrue(result)
    }

    @Test
    fun decreaseCart() {
        every { cr.decreaseCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.decreaseCart(mockk())
        verify { cr.decreaseCart(any()) }
    }

    @Test
    fun removeCart() {
        every { cr.deleteCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.removeCart(mockk())
        verify { cr.deleteCart(any()) }
    }

    @Test
    fun increaseCart() {
        every { cr.increaseCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.increaseCart(mockk())
        verify { cr.increaseCart(any()) }
    }

    @Test
    fun setCartNotes() {
        every { cr.setCartNotes(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.setCartNotes(mockk())
        verify { cr.setCartNotes(any()) }
    }
}
