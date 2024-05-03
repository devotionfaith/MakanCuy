package com.devotion.makancuy.data.datasource.user

import com.devotion.makancuy.data.source.local.pref.UserPreference

interface UserDataSource {
    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingGridMode: Boolean)
}

class UserDataSourceImpl(
    private val pref: UserPreference,
) : UserDataSource {
    override fun isUsingGridMode(): Boolean = pref.isUsingGridMode()

    override fun setUsingGridMode(isUsingGridMode: Boolean) = pref.setUsingGridMode(isUsingGridMode)
}
