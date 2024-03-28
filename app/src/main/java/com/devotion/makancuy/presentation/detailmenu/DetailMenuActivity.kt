package com.devotion.makancuy.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.devotion.makancuy.R
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.databinding.ActivityDetailMenuBinding
import com.devotion.makancuy.utils.fromCurrencyToDouble
import com.devotion.makancuy.utils.toIndonesianFormat

class DetailMenuActivity : AppCompatActivity() {
    private var amount: Int = 1
    private var location: String = "Dummy"

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClick()
        bindMenuDetail()
        updateLayout(amount)
        setContentView(binding.root)
    }

    private fun setOnClick() {
        binding.cvSubstract.setOnClickListener {
            updateAmount(amount - 1)
        }

        binding.cvAdd.setOnClickListener {
            updateAmount(amount + 1)
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.tvLocationAddress.setOnClickListener {
            navigateToMaps()
        }
    }

    private fun navigateToMaps() {
        val gmmIntentUri = Uri.parse(location)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        startActivity(mapIntent)
    }

    private fun updateAmount(currentAmount: Int) {
        val newAmount = maxOf(0, currentAmount)
        amount = newAmount
        binding.tvAmount.text = newAmount.toString()
        updateLayout(newAmount)
        binding.btnAddToCart.isEnabled = newAmount != 0
    }

    private fun updateLayout(amount: Int) {
        val price = binding.tvMenuPriceDetails.text.toString().fromCurrencyToDouble() ?: 0.0
        val totalPrice = price * amount
        val formattedTotalPrice = totalPrice.toIndonesianFormat()
        val buttonText = getString(R.string.text_add_to_cart, formattedTotalPrice)
        binding.btnAddToCart.text = buttonText
    }


    private fun bindMenuDetail() {
        intent.extras?.getParcelable<Menu>(EXTRAS_ITEM)?.let {
            location = it.locationUrl
            binding.ivMenuImgDetails.load(it.imageurl) {
                crossfade(true)
            }
            binding.tvMenuNameDetails.text = it.name
            binding.tvMenuPriceDetails.text = it.price.toIndonesianFormat()
            binding.tvMenuDetails.text = it.details
            binding.tvLocationAddress.text = it.locationAddress
        }
    }
}