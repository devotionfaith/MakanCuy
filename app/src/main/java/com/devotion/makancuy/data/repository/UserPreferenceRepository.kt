package com.devotion.makancuy.data.repository

import com.devotion.makancuy.data.datasource.userpref.UserPreferenceDataSource

interface UserPreferenceRepository {
    fun isUsingGridMode(): Boolean
    fun setUsingGridMode(isUsingGridMode: Boolean)
}
class UserPreferenceRepositoryImpl(private val userDataSource: UserPreferenceDataSource) : UserPreferenceRepository {
    override fun isUsingGridMode(): Boolean {
        return userDataSource.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        return userDataSource.setUsingGridMode(isUsingGridMode)
    }
}
