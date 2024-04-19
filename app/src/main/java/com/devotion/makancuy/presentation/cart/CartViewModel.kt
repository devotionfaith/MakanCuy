package com.devotion.makancuy.presentation.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.devotion.makancuy.data.model.Cart
import com.devotion.makancuy.data.repository.CartRepository
import com.devotion.makancuy.data.repository.UserRepository
import com.devotion.makancuy.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: CartRepository, private val userRepository: UserRepository) : ViewModel() {
    fun getCartList() = cartRepository.getUserCartData().asLiveData(Dispatchers.IO)
    fun isLogin() = userRepository.isLoggedIn()
    fun decreaseCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.decreaseCart(item).collect()
        }
    }

    fun increaseCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.increaseCart(item).collect()
        }
    }

    fun removeCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteCart(item).collect()
        }
    }

    fun setCartNotes(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.setCartNotes(item).collect {
                Log.d("Set Notes", "setCartNotes: $it")
            }
        }
    }
}