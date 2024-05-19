package com.devotion.makancuy.data.repository

import app.cash.turbine.test
import com.devotion.makancuy.data.datasource.auth.AuthDataSource
import com.devotion.makancuy.data.model.User
import com.devotion.makancuy.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    @MockK
    lateinit var ds: AuthDataSource

    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserRepositoryImpl(ds)
    }

    @Test
    fun doLogin_success() {
        val email = "misal"
        val pw = "abcd"
        coEvery { ds.doLogin(any(), any()) } returns true
        runTest {
            repository.doLogin(email, pw).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { ds.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doLogin_loading() {
        val email = "misal"
        val pw = "abcd"
        coEvery { ds.doLogin(any(), any()) } returns true
        runTest {
            repository.doLogin(email, pw).map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { ds.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doLogin_error() {
        val email = "misal"
        val pw = "abcd"
        coEvery { ds.doLogin(any(), any()) } throws IllegalStateException("Mock Error")
        runTest {
            repository.doLogin(email, pw).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { ds.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doRegister_success() {
        val email = "misal"
        val name = "kamu"
        val pw = "abcd"
        coEvery { ds.doRegister(any(), any(), any()) } returns true
        runTest {
            repository.doRegister(email, name, pw).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { ds.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun doRegister_loading() {
        val email = "misal"
        val name = "kamu"
        val pw = "abcd"
        coEvery { ds.doRegister(any(), any(), any()) } returns true
        runTest {
            repository.doRegister(email, name, pw).map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { ds.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun doRegister_error() {
        val email = "misal"
        val name = "kamu"
        val pw = "abcd"
        coEvery { ds.doRegister(any(), any(), any()) } throws IllegalStateException("Mock Error")
        runTest {
            repository.doRegister(email, name, pw).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { ds.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun updateProfile_success() {
        runTest {
            coEvery { ds.updateProfile(any()) } returns true
            repository.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                coVerify { ds.updateProfile(any()) }
            }
        }
    }

    @Test
    fun updateProfile_loading() {
        runTest {
            coEvery { ds.updateProfile(any()) } returns true
            repository.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(110)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { ds.updateProfile(any()) }
            }
        }
    }

    @Test
    fun updateProfile_error() {
        runTest {
            coEvery { ds.updateProfile(any()) } throws IllegalStateException("Mock Error")
            repository.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { ds.updateProfile(any()) }
            }
        }
    }

    @Test
    fun updatePassword_success() {
        runTest {
            coEvery { ds.updatePassword(any()) } returns true
            repository.updatePassword("password").map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                coVerify { ds.updatePassword(any()) }
            }
        }
    }

    @Test
    fun updatePassword_loading() {
        runTest {
            coEvery { ds.updatePassword(any()) } returns true
            repository.updatePassword("password").map {
                delay(100)
                it
            }.test {
                delay(110)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { ds.updatePassword(any()) }
            }
        }
    }

    @Test
    fun updatePassword_error() {
        runTest {
            coEvery { ds.updatePassword(any()) } throws IllegalStateException("Mock Error")
            repository.updatePassword("password").map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { ds.updatePassword(any()) }
            }
        }
    }

    @Test
    fun updateEmail_success() {
        runTest {
            coEvery { ds.updateEmail(any()) } returns true
            repository.updateEmail("email").map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                coVerify { ds.updateEmail(any()) }
            }
        }
    }

    @Test
    fun updateEmail_loading() {
        runTest {
            coEvery { ds.updateEmail(any()) } returns true
            repository.updateEmail("email").map {
                delay(100)
                it
            }.test {
                delay(110)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { ds.updateEmail(any()) }
            }
        }
    }

    @Test
    fun updateEmail_error() {
        runTest {
            coEvery { ds.updateEmail(any()) } throws IllegalStateException("Mock Error")
            repository.updateEmail("email").map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { ds.updateEmail(any()) }
            }
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        every { ds.requestChangePasswordByEmail() } returns true
        assertTrue(repository.requestChangePasswordByEmail())
        verify { ds.requestChangePasswordByEmail() }
    }

    @Test
    fun doLogout() {
        coEvery { ds.doLogout() } returns true
        runTest {
            val result =
                repository.doLogout().map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Success)
                }
            coVerify { ds.doLogout() }
            assertEquals(Unit, result)
        }
    }

    @Test
    fun isLoggedIn() {
        every { ds.isLoggedIn() } returns true
        val result = repository.isLoggedIn()
        assertTrue(result)
        verify { ds.isLoggedIn() }
    }

    @Test
    fun getCurrentUser_not_null() {
        val mockUser = mockk<User>()
        every { ds.getCurrentUser() } returns mockUser
        val result = repository.getCurrentUser()
        assertEquals(mockUser, result)
        verify { ds.getCurrentUser() }
    }

    @Test
    fun getCurrentUser_null() {
        val mockUser = null
        every { ds.getCurrentUser() } returns mockUser
        val result = repository.getCurrentUser()
        assertEquals(mockUser, result)
        verify { ds.getCurrentUser() }
    }
}
