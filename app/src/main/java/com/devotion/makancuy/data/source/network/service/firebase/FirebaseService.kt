package com.devotion.makancuy.data.source.network.service.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

interface FirebaseService {

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(email: String, password: String): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(email: String, fullName: String, password: String): Boolean
    suspend fun doLogout(): Boolean
    fun isLoggedIn(): Boolean
    fun getCurrentUser(): FirebaseUser?
}

class FirebaseServiceImpl(): FirebaseService{

    private val firebaseAuth = FirebaseAuth.getInstance()
    override suspend fun doLogin(email: String, password: String): Boolean {
        val loginResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return loginResult.user != null
    }

    override suspend fun doRegister(email: String, fullName: String, password: String): Boolean {
        val registerResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        registerResult.user?.updateProfile(
            userProfileChangeRequest {
                displayName = fullName
            }
        )?.await()
        return registerResult.user != null
    }

    override suspend fun doLogout(): Boolean {
        Firebase.auth.signOut()
        return true
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}