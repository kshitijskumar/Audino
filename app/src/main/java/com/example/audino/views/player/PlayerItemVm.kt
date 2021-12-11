package com.example.audino.views.player

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.example.audino.R
import com.example.audino.model.response.BookResponse
import com.example.audino.viewmodels.PlaybackSpeedLevel
import com.example.audino.viewmodels.SleepTimerLevel

class PlayerItemVm(val playerClickCallback: PlayerFragment.PlayerFragmentClickCallback) {

    val book = ObservableField<BookResponse>()

    val isBookSaved = ObservableBoolean(false)

    fun initBook(bookResponse: BookResponse) {
        book.set(bookResponse)
    }

    fun saveUnsaveBook() {
        playerClickCallback.onSaveClick(isBookSaved.get())
        isBookSaved.set(!isBookSaved.get())
    }

    companion object {
        @JvmStatic
        @BindingAdapter("sleepTimerDuration")
        fun setSleepTimerDurationText(tv: TextView, level: SleepTimerLevel) {
            val resId = when(level) {
                SleepTimerLevel.NoTimer -> R.string.sleep_timer
                SleepTimerLevel.Timer15 -> R.string.sleep_timer_15
                SleepTimerLevel.Timer30 -> R.string.sleep_timer_30
                SleepTimerLevel.Timer45 -> R.string.sleep_timer_45
            }
            tv.setText(resId)
        }

        @JvmStatic
        @BindingAdapter("playbackSpeedText")
        fun setPlaybackSpeedText(tv: TextView, speed: PlaybackSpeedLevel) {
            val resId = when(speed) {
                PlaybackSpeedLevel.Speed05 -> R.string.speed_05x
                PlaybackSpeedLevel.Speed1 -> R.string.speed_1x
                PlaybackSpeedLevel.Speed15 -> R.string.speed_15x
                PlaybackSpeedLevel.Speed2 -> R.string.speed_2x
            }
            tv.setText(resId)
        }
    }
}