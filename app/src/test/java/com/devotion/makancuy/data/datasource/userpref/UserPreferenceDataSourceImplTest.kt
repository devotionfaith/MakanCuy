package com.devotion.makancuy.data.datasource.userpref

import com.devotion.makancuy.data.source.local.pref.UserPreference
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserPreferenceDataSourceImplTest {
    @MockK
    lateinit var preference: UserPreference
    private lateinit var dataSource: UserPreferenceDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = UserPreferenceDataSourceImpl(preference)
    }

    @Test
    fun isUsingGridMode() {
        every { preference.isUsingGridMode() } returns true

        val result = dataSource.isUsingGridMode()
        verify { preference.isUsingGridMode() }
        assertEquals(true, result)
    }

    @Test
    fun setUsingGridMode() {
        every { preference.setUsingGridMode(any()) } returns Unit
        dataSource.setUsingGridMode(true)
        verify { preference.setUsingGridMode(any()) }
    }
}
