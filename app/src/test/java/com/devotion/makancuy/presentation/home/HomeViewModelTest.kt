package com.devotion.makancuy.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devotion.makancuy.data.repository.CategoryRepository
import com.devotion.makancuy.data.repository.MenuRepository
import com.devotion.makancuy.data.repository.UserPreferenceRepository
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
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val courotineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cr: CategoryRepository

    @MockK
    lateinit var mr: MenuRepository

    @MockK
    lateinit var upr: UserPreferenceRepository

    @MockK
    lateinit var ur: UserRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(HomeViewModel(cr, mr, upr, ur))
    }

    @Test
    fun isUsingGridMode() {
        every { upr.isUsingGridMode() } returns true
        val result = viewModel.isUsingGridMode()
        assertEquals(true, result)
        verify { upr.isUsingGridMode() }
    }

    @Test
    fun setUsingGridMode() {
        every { upr.setUsingGridMode(any()) } returns Unit
        viewModel.setUsingGridMode(false)
        verify { upr.setUsingGridMode(any()) }
    }

    @Test
    fun getListMode() {
    }

    @Test
    fun changeListMode() {
    }

    @Test
    fun getCurrentUsername() {
    }

    @Test
    fun getMenu() {
        every { mr.getMenu(any()) } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getMenu().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { mr.getMenu(any()) }
    }

    @Test
    fun getCategory() {
        every { cr.getCategories() } returns
            flow {
                emit(
                    ResultWrapper.Success(listOf(mockk(relaxed = true), mockk(relaxed = true))),
                )
            }
        val result = viewModel.getCategory().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }
}
