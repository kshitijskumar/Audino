package com.example.audino.views.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.os.bundleOf
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.audino.R
import com.example.audino.databinding.ActivityMainBinding
import com.example.audino.model.response.BookResponse
import com.example.audino.service.AudinoServiceConnection
import com.example.audino.utils.Constants
import com.example.audino.utils.Constants.ACTION_PLAYER_PLAYING_STATE_CHANGED
import com.example.audino.utils.Constants.ACTION_SEND_PENDING_BROADCAST
import com.example.audino.viewmodels.MainViewModel
import com.example.audino.views.callbacks.SwitchFragmentCallback
import com.example.audino.views.home.HomeFragment
import com.example.audino.views.player.PlayerFragment
import com.example.audino.views.read.ReadFragment
import com.example.audino.views.read.ReadFragment.Companion.BOOK_ID

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

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(applicationContext)
    }

    private val localBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("PlayPauseId", "onReceive")
            when(intent?.action) {
                ACTION_PLAYER_PLAYING_STATE_CHANGED -> {
                    Log.d("PlayPauseId", "received: ${intent.getBooleanExtra("isPlaying", false)}")
                    mainViewModel.togglePlayState(intent.getBooleanExtra("isPlaying", false))
                }
            }
        }
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
            Log.d("PlayBook", "currBook: ${it?.getText(METADATA_KEY_MEDIA_ID)}")
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

    override fun openReadFragment(bookId: String) {
        val readFragment = ReadFragment.newInstance(getReadFragmentArgs(bookId))
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, readFragment, "ReadFragment")
            .addToBackStack(null)
            .commit()
    }

    private fun getReadFragmentArgs(bookId: String) : Bundle {
        return bundleOf(BOOK_ID to bookId)
    }

    override fun onStart() {
        super.onStart()
        localBroadcastManager
            .registerReceiver(localBroadcastReceiver,
                IntentFilter(ACTION_PLAYER_PLAYING_STATE_CHANGED))
        sendPendingBroadcasts()
    }

    override fun onStop() {
        super.onStop()
        localBroadcastManager.unregisterReceiver(localBroadcastReceiver)
    }

    private fun sendPendingBroadcasts() {
        localBroadcastManager.sendBroadcast(Intent(ACTION_SEND_PENDING_BROADCAST))
    }
}