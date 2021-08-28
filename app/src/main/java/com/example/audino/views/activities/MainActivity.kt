package com.example.audino.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.audino.R
import com.example.audino.databinding.ActivityMainBinding
import com.example.audino.views.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment by lazy {
        HomeFragment.newInstance(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragmentTransaction()
    }

    private fun setupFragmentTransaction() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, homeFragment, HomeFragment.TAG)
            .commitNow()
    }

}