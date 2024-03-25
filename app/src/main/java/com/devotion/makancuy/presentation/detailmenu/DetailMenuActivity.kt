package com.devotion.makancuy.presentation.detailmenu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devotion.makancuy.R
import com.devotion.makancuy.databinding.ActivityDetailMenuBinding

class DetailMenuActivity : AppCompatActivity() {
    private val binding : ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}