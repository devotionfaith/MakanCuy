package com.devotion.makancuy.presentation.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.devotion.makancuy.R
import com.devotion.makancuy.data.datasource.auth.AuthDataSource
import com.devotion.makancuy.data.datasource.auth.FirebaseAuthDataSource
import com.devotion.makancuy.data.datasource.cart.CartDataSource
import com.devotion.makancuy.data.datasource.cart.CartDatabaseDataSource
import com.devotion.makancuy.data.datasource.menu.MenuApiDataSource
import com.devotion.makancuy.data.datasource.menu.MenuDataSource
import com.devotion.makancuy.data.repository.CartRepository
import com.devotion.makancuy.data.repository.CartRepositoryImpl
import com.devotion.makancuy.data.repository.MenuRepository
import com.devotion.makancuy.data.repository.MenuRepositoryImpl
import com.devotion.makancuy.data.repository.UserRepository
import com.devotion.makancuy.data.repository.UserRepositoryImpl
import com.devotion.makancuy.data.source.local.database.AppDatabase
import com.devotion.makancuy.data.source.network.service.RestaurantApiService
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseService
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseServiceImpl
import com.devotion.makancuy.databinding.ActivityCheckoutBinding
import com.devotion.makancuy.databinding.LayoutDialogBinding
import com.devotion.makancuy.presentation.checkout.adapter.PriceListAdapter
import com.devotion.makancuy.presentation.common.adapter.CartListAdapter
import com.devotion.makancuy.presentation.main.MainActivity
import com.devotion.makancuy.utils.GenericViewModelFactory
import com.devotion.makancuy.utils.proceedWhen
import com.devotion.makancuy.utils.toIndonesianFormat
import kotlinx.coroutines.delay

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDataSource: CartDataSource = CartDatabaseDataSource(database.cartDao())
        val cartRepository: CartRepository = CartRepositoryImpl(cartDataSource)
        val service = RestaurantApiService.invoke()
        val menuDataSource : MenuDataSource = MenuApiDataSource(service)
        val menuRepository : MenuRepository = MenuRepositoryImpl(menuDataSource)
        val firebaseService: FirebaseService = FirebaseServiceImpl()
        val authDataSource: AuthDataSource = FirebaseAuthDataSource(firebaseService)
        val userRepository: UserRepository = UserRepositoryImpl(authDataSource)
        GenericViewModelFactory.create(
            CheckoutViewModel(
                cartRepository,
                menuRepository,
                userRepository)
        )
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }
    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnCheckout.setOnClickListener {
            doCheckout()
        }
    }

    private fun doCheckout() {
        viewModel.checkoutCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    dialogCheckoutSuccess(this)
                },
                doOnError = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(
                        this,
                        getString(R.string.text_check_out_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("CheckoutFailed", "Checkout Failed : ${it.exception?.message.orEmpty()}", it.exception)

                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                }
            )
        }
    }

    private fun dialogCheckoutSuccess(context: Context) {
        val dialogBinding = LayoutDialogBinding.inflate(LayoutInflater.from(context))
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setView(dialogBinding.root)
        val dialog = alertDialogBuilder.create()
        dialogBinding.btnBackToHome.setOnClickListener {
            deleteCart()
            startActivity(Intent(context, MainActivity::class.java))
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun deleteCart() {
        viewModel.deleteAllCart()
    }

    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
        binding.layoutContent.rvShoppingSummary.adapter = priceItemAdapter
    }

    private fun observeData() {
        viewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(doOnSuccess = {
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = true
                binding.layoutContent.rvCart.isVisible = true
                binding.cvOrderAction.isVisible = true
                binding.btnCheckout.isEnabled = true
                result.payload?.let { (carts, priceItems, totalPrice) ->
                    adapter.submitData(carts)
                    binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    priceItemAdapter.submitData(priceItems)
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvOrderAction.isVisible = false
                binding.btnCheckout.isEnabled = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvOrderAction.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                data.payload?.let { (_, _, totalPrice) ->
                    binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                }
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvOrderAction.isVisible = false
            })
        }
    }
}