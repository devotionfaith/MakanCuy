package com.devotion.makancuy.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.devotion.makancuy.data.repository.CategoryRepository
import com.devotion.makancuy.data.repository.MenuRepository
import com.devotion.makancuy.data.repository.UserPreferenceRepository
import com.devotion.makancuy.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userPreferenceRepository: UserPreferenceRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    fun isUsingGridMode() = userPreferenceRepository.isUsingGridMode()

    fun setUsingGridMode(isUsingGridMode: Boolean) = userPreferenceRepository.setUsingGridMode(isUsingGridMode)

    fun getListMode(): Int {
        return if (isUsingGridMode()) 1 else 0
    }

    fun changeListMode() {
        setUsingGridMode(!isUsingGridMode())
    }

    fun getCurrentUsername() = userRepository.getCurrentUser()?.fullName

    fun getMenu(categoryName: String? = null) = menuRepository.getMenu(categoryName).asLiveData(Dispatchers.IO)

    fun getCategory() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)
}
