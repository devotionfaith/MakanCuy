package com.devotion.makancuy.data.repository

import com.devotion.makancuy.data.datasource.userpref.UserPreferenceDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserPreferenceRepositoryImplTest {
    @MockK
    lateinit var ds: UserPreferenceDataSource
    lateinit var repo: UserPreferenceRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = UserPreferenceRepositoryImpl(ds)
    }

    @Test
    fun isUsingGridMode() {
        every { ds.isUsingGridMode() } returns true
        val result = repo.isUsingGridMode()
        verify { ds.isUsingGridMode() }
        assertEquals(true, result)
    }

    @Test
    fun setUsingGridMode() {
        every { ds.setUsingGridMode(any()) } returns Unit
        repo.setUsingGridMode(true)
        verify { ds.setUsingGridMode(any()) }
    }
}
