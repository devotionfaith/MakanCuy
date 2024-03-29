package com.devotion.makancuy.presentation.popup

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devotion.makancuy.R
import com.devotion.makancuy.databinding.ActivityPopupCheckoutBinding
import com.devotion.makancuy.presentation.main.MainActivity

class PopupCheckoutActivity : AppCompatActivity() {
    private val binding: ActivityPopupCheckoutBinding by lazy {
        ActivityPopupCheckoutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClick()
    }

    private fun setOnClick() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}