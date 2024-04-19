package com.devotion.makancuy.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.devotion.makancuy.R
import com.devotion.makancuy.data.datasource.auth.AuthDataSource
import com.devotion.makancuy.data.datasource.auth.FirebaseAuthDataSource
import com.devotion.makancuy.data.repository.UserRepository
import com.devotion.makancuy.data.repository.UserRepositoryImpl
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseService
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseServiceImpl
import com.devotion.makancuy.databinding.ActivityMainBinding
import com.devotion.makancuy.presentation.login.LoginActivity
import com.devotion.makancuy.presentation.login.LoginViewModel
import com.devotion.makancuy.utils.GenericViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel : MainViewModel by viewModels {
        val s : FirebaseService = FirebaseServiceImpl()
        val ds : AuthDataSource = FirebaseAuthDataSource(s)
        val r : UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(MainViewModel(r))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setBottomNav()
    }

    private fun setBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.menu_tab_profile -> {
                    if(!viewModel.isLogin()){
                        navigateToLogin()
                        controller.navigate(R.id.menu_tab_home)
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}