package com.devotion.makancuy.data.datasource.auth

import com.devotion.makancuy.data.mapper.toUser
import com.devotion.makancuy.data.model.User
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseService
import java.lang.Exception

interface AuthDataSource {

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(email: String, password: String): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(email: String, fullName: String, password: String): Boolean
    suspend fun doLogout(): Boolean
    fun isLoggedIn(): Boolean
    fun getCurrentUser(): User?
}

class FirebaseAuthDataSource(private val service: FirebaseService) : AuthDataSource{
    override suspend fun doLogin(email: String, password: String): Boolean {
        return service.doLogin(email, password)
    }

    override suspend fun doRegister(email: String, fullName: String, password: String): Boolean {
        return service.doRegister(email, fullName, password)
    }

    override suspend fun doLogout(): Boolean {
        return service.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return service.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return service.getCurrentUser().toUser()
    }

}
