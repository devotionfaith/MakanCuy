package com.devotion.makancuy.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.devotion.makancuy.R
import com.devotion.makancuy.data.datasource.cart.CartDataSource
import com.devotion.makancuy.data.datasource.cart.CartDatabaseDataSource
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.data.repository.CartRepository
import com.devotion.makancuy.data.repository.CartRepositoryImpl
import com.devotion.makancuy.data.source.local.database.AppDatabase
import com.devotion.makancuy.databinding.ActivityDetailMenuBinding
import com.devotion.makancuy.utils.GenericViewModelFactory
import com.devotion.makancuy.utils.proceedWhen
import com.devotion.makancuy.utils.toIndonesianFormat

class DetailMenuActivity : AppCompatActivity() {

    companion object {
        const val EXTRAS_ITEM = "EXTRAS_DETAIL_DATA"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRAS_ITEM, menu)
            context.startActivity(intent)
        }
    }

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(
            DetailMenuViewModel(intent?.extras, rp)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClick()
        bindMenuDetail()
        observeData()
        setContentView(binding.root)
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.btnAddToCart.isEnabled = it != 0.0
            binding.btnAddToCart.text =
                getString(R.string.text_add_to_cart, it.toIndonesianFormat())
        }
        viewModel.menuCountLiveData.observe(this) {
            binding.tvAmount.text = it.toString()
        }
    }

    private fun setOnClick() {
        binding.cvSubstract.setOnClickListener {
            viewModel.minus()
        }
        binding.cvAdd.setOnClickListener {
            viewModel.add()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.tvLocationAddress.setOnClickListener {
            navigateToMaps()
        }
        binding.btnAddToCart.setOnClickListener {
            addMenuToCart()
        }
    }

    private fun addMenuToCart() {
        viewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_adding_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_failed_add),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_loading),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun navigateToMaps() {
        startActivity(Intent(Intent.ACTION_VIEW, viewModel.getLocationUrl()))
    }

    private fun bindMenuDetail() {
        intent.extras?.getParcelable<Menu>(EXTRAS_ITEM)?.let {
            binding.ivMenuImgDetails.load(it.imageUrl) {
                crossfade(true)
            }
            binding.tvMenuNameDetails.text = it.name
            binding.tvMenuPriceDetails.text = it.price.toIndonesianFormat()
            binding.tvMenuDetails.text = it.details
            binding.tvLocationAddress.text = it.locationAddress
        }
    }
}