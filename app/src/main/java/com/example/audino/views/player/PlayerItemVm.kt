package com.example.audino.views.player

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.example.audino.R
import com.example.audino.model.response.BookResponse
import com.example.audino.viewmodels.SleepTimerLevel

class PlayerItemVm(val playerClickCallback: PlayerFragment.PlayerFragmentClickCallback) {

    val book = ObservableField<BookResponse>()

    fun initBook(bookResponse: BookResponse) {
        book.set(bookResponse)
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
    }
}