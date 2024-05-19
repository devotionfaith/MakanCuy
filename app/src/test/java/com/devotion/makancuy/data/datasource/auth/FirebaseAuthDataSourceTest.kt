package com.devotion.makancuy.data.datasource.auth

import com.devotion.makancuy.data.source.network.service.firebase.FirebaseService
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceTest {
    @MockK
    lateinit var service: FirebaseService
    private lateinit var dataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FirebaseAuthDataSource(service)
    }

    @Test
    fun doLogin() {
        runTest {
            val email = "email"
            val password = "password"
            coEvery { service.doLogin(email, password) } returns true
            val result = dataSource.doLogin(email, password)
            assertTrue(result)
            coVerify { service.doLogin(email, password) }
        }
    }

    @Test
    fun doRegister() {
        runTest {
            val email = "email"
            val fullName = "name"
            val password = "password"
            coEvery { service.doRegister(email, fullName, password) } returns true
            val result = dataSource.doRegister(email, fullName, password)
            assertTrue(result)
            coVerify { service.doRegister(email, fullName, password) }
        }
    }

    @Test
    fun updateProfile() {
        runTest {
            val fullName = "nama"
            coEvery { service.updateProfile(fullName) } returns true
            val result = dataSource.updateProfile(fullName)
            assertTrue(result)
            coVerify { service.updateProfile(fullName) }
        }
    }

    @Test
    fun updatePassword() {
        runTest {
            val newPassword = "password"
            coEvery { service.updatePassword(newPassword) } returns true
            val result = dataSource.updatePassword(newPassword)
            assertTrue(result)
            coVerify { service.updatePassword(newPassword) }
        }
    }

    @Test
    fun updateEmail() {
        runTest {
            val newEmail = "email"
            coEvery { service.updateEmail(newEmail) } returns true
            val result = dataSource.updateEmail(newEmail)
            assertTrue(result)
            coVerify { service.updateEmail(newEmail) }
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        every { service.requestChangePasswordByEmail() } returns true
        val result = dataSource.requestChangePasswordByEmail()
        assertTrue(result)
        verify { service.requestChangePasswordByEmail() }
    }

    @Test
    fun doLogout() {
        runTest {
            coEvery { service.doLogout() } returns true
            val result = dataSource.doLogout()
            assertTrue(result)
            coVerify { service.doLogout() }
        }
    }

    @Test
    fun isLoggedIn() {
        every { service.isLoggedIn() } returns true
        val result = dataSource.isLoggedIn()
        assertTrue(result)
        verify { service.isLoggedIn() }
    }

    @Test
    fun getCurrentUser() {
        runTest {
            val name = "nama"
            val email = "email"
            val firebaseUser = mockk<FirebaseUser>()
            every { firebaseUser.displayName } returns name
            every { firebaseUser.email } returns email
            every { service.getCurrentUser() } returns firebaseUser
            val result = dataSource.getCurrentUser()
            assertEquals(name, result?.fullName)
            assertEquals(email, result?.email)
            verify { service.getCurrentUser() }
        }
    }
}
