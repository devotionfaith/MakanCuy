package com.devotion.makancuy.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devotion.makancuy.data.repository.UserRepository
import com.devotion.makancuy.tools.MainCoroutineRule
import com.devotion.makancuy.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RegisterViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var ur: UserRepository

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(RegisterViewModel(ur))
    }

    @Test
    fun doRegister() {
        val name = "nama"
        val email = "email"
        val pw = "pw"
        every { ur.doRegister(any(), any(), any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.doRegister(name, email, pw)
        verify { ur.doRegister(any(), any(), any()) }
    }
}
