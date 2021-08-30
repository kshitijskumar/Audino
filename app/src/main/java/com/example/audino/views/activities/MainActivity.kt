package com.example.audino.views.activities

import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.audino.R
import com.example.audino.databinding.ActivityMainBinding
import com.example.audino.service.AudinoServiceConnection
import com.example.audino.viewmodels.MainViewModel
import com.example.audino.views.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment by lazy {
        HomeFragment.newInstance(null)
    }

    private val mainViewModel by lazy {
        MainViewModel.getMainViewModel(this, serviceConnection)
    }

    private val serviceConnection by lazy {
        AudinoServiceConnection(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        volumeControlStream = AudioManager.STREAM_MUSIC
        setupFragmentTransaction()

//        observeValues()
    }

    private fun observeValues() {
        mainViewModel.genresList.observe(this) {
            Log.d("GenresList", "result: $it")
        }
    }

    private fun setupFragmentTransaction() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, homeFragment, HomeFragment.TAG)
            .commitNow()
    }

}