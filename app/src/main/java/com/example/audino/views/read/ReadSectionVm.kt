package com.example.audino.views.read

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.example.audino.R
import com.example.audino.utils.Constants.PREFERRED_BG_COLOR
import com.example.audino.utils.Constants.PREFERRED_TEXT_SIZE
import com.example.audino.utils.Injector

class ReadSectionVm {

    val textSizeOption = ObservableField(TextSizeOptions.TextSmall)
    val bgOption = ObservableField(BackgroundOption.BgLight)

    fun updateTextSizeOption(textSizeOption: TextSizeOptions) {
        this.textSizeOption.set(textSizeOption)
    }

    fun updateBgOption(bgOption: BackgroundOption) {
        this.bgOption.set(bgOption)
    }

    init {
        val pref = Injector.getInjector().provideSharedPrefs()
        val textSize = pref.getInt(PREFERRED_TEXT_SIZE, 0)
        val bg = pref.getInt(PREFERRED_BG_COLOR, 0)
        textSizeOption.set(integerToTextOption(textSize))
        bgOption.set(integerToBgOption(bg))
    }

    private fun integerToTextOption(prefVal: Int) : TextSizeOptions {
        return when(prefVal) {
            0 -> TextSizeOptions.TextSmall
            1 -> TextSizeOptions.TextMedium
            2 -> TextSizeOptions.TextLarge
            else -> TextSizeOptions.TextSmall
        }
    }

    private fun integerToBgOption(prefValue: Int) : BackgroundOption {
        return when(prefValue) {
            0 -> BackgroundOption.BgLight
            1 -> BackgroundOption.BgDark
            else -> BackgroundOption.BgLight
        }
    }

    companion object {

        @JvmStatic
        @BindingAdapter("preferredTextSize")
        fun changePreferredTextSize(tv: TextView, textSizeOption: TextSizeOptions) {
            val pref = Injector.getInjector().provideSharedPrefs()
            when (textSizeOption) {
                TextSizeOptions.TextSmall -> {
                    tv.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, tv.context.resources.getDimension(
                            R.dimen.text_small
                        )
                    )
                    updateTextSizeToPref(0)
                }
                TextSizeOptions.TextMedium -> {
                    tv.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, tv.context.resources.getDimension(
                            R.dimen.text_medium
                        )
                    )
                    updateTextSizeToPref(1)
                }
                TextSizeOptions.TextLarge -> {
                    tv.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, tv.context.resources.getDimension(
                            R.dimen.text_large
                        )
                    )
                    updateTextSizeToPref(2)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("preferredBackgroundColor")
        fun changePreferredBackgroundColor(root: View, bgOption: BackgroundOption) {
            when(bgOption) {
                BackgroundOption.BgLight -> {
                    root.setBackgroundColor(ResourcesCompat.getColor(root.resources, R.color.white, null))
                    updateBgColorToPref(0)
                }
                BackgroundOption.BgDark -> {
                    root.setBackgroundColor(ResourcesCompat.getColor(root.resources, R.color.gray, null))
                    updateBgColorToPref(1)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("preferredTextColor")
        fun changePreferredTextColor(tv: TextView, bgOption: BackgroundOption) {
            when(bgOption) {
                BackgroundOption.BgLight -> {
                    tv.setTextColor(ResourcesCompat.getColor(tv.resources, R.color.black, null))
                }
                BackgroundOption.BgDark -> {
                    tv.setTextColor(ResourcesCompat.getColor(tv.resources, R.color.white, null))
                }
            }
        }

        private fun updateTextSizeToPref(prefValue: Int) {
            Injector.getInjector().provideSharedPrefs()
                .edit()
                .putInt(PREFERRED_TEXT_SIZE, prefValue)
                .apply()
        }

        private fun updateBgColorToPref(prefValue: Int) {
            Injector.getInjector().provideSharedPrefs()
                .edit()
                .putInt(PREFERRED_BG_COLOR, prefValue)
                .apply()
        }
    }
}