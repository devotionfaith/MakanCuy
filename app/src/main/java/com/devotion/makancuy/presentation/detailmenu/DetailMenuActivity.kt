package com.devotion.makancuy.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import com.devotion.makancuy.R
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.databinding.ActivityDetailMenuBinding
import com.devotion.makancuy.utils.proceedWhen
import com.devotion.makancuy.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailMenuActivity : AppCompatActivity() {
    companion object {
        const val EXTRAS_ITEM = "EXTRAS_DETAIL_DATA"

        fun startActivity(
            context: Context,
            menu: Menu,
        ) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRAS_ITEM, menu)
            context.startActivity(intent)
        }
    }

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModel {
        parametersOf(intent.extras)
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
                        Toast.LENGTH_SHORT,
                    ).show()
                    binding.pbLoadingAddToCart.isVisible = false
                    finish()
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_failed_add),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoadingAddToCart.isVisible = true
                    binding.btnAddToCart.isEnabled = false
                },
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
            binding.tvMenuPriceDetails.text = it.formattedprice
            binding.tvMenuDetails.text = it.details
            binding.tvLocationAddress.text = it.locationAddress
        }
    }
}
