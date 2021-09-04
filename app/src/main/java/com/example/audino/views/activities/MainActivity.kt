package com.example.audino.views.activities

import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.example.audino.R
import com.example.audino.databinding.ActivityMainBinding
import com.example.audino.model.response.BookResponse
import com.example.audino.service.AudinoServiceConnection
import com.example.audino.viewmodels.MainViewModel
import com.example.audino.views.callbacks.SwitchFragmentCallback
import com.example.audino.views.home.HomeFragment
import com.example.audino.views.player.PlayerFragment

class MainActivity : AppCompatActivity(), SwitchFragmentCallback {

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

        observeValues()
    }

    private fun observeValues() {
//        mainViewModel.genresList.observe(this) {
//            Log.d("GenresList", "result: $it")
//        }
        mainViewModel.currBook.observe(this) {
            Log.d("PlayBook", "currBook: $it")
        }
        mainViewModel.playbackState.observe(this) {
            Log.d("PlayBook", "playback state: $it")
            PlaybackStateCompat.STATE_PAUSED
        }
    }

    private fun setupFragmentTransaction() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, homeFragment, HomeFragment.TAG)
            .commitNow()
    }

    override fun openPlayerFragment(book: BookResponse) {
        Log.d("ClickEvent", "in acitivity")
        val bundle = Bundle().apply {
            putSerializable("Book", book)
        }
        val playerFragment = PlayerFragment.newInstance(bundle)
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, playerFragment, "PlayerFragment")
            .addToBackStack(null)
            .commit()
    }
}