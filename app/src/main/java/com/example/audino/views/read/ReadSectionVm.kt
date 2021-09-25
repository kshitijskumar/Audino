package com.example.audino.views.read

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.example.audino.R

class ReadSectionVm {

    val textSizeOption = ObservableField(TextSizeOptions.TextSmall)
    val bgOption = ObservableField(BackgroundOption.BgLight)

    fun updateTextSizeOption(textSizeOption: TextSizeOptions) {
        this.textSizeOption.set(textSizeOption)
    }

    fun updateBgOption(bgOption: BackgroundOption) {
        this.bgOption.set(bgOption)
    }


    companion object {

        @JvmStatic
        @BindingAdapter("preferredTextSize")
        fun changePreferredTextSize(tv: TextView, textSizeOption: TextSizeOptions) {
            when (textSizeOption) {
                TextSizeOptions.TextSmall -> tv.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX, tv.context.resources.getDimension(
                        R.dimen.text_small
                    )
                )
                TextSizeOptions.TextMedium -> tv.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX, tv.context.resources.getDimension(
                        R.dimen.text_medium
                    )
                )
                TextSizeOptions.TextLarge -> tv.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX, tv.context.resources.getDimension(
                        R.dimen.text_large
                    )
                )
            }
        }

        @JvmStatic
        @BindingAdapter("preferredBackgroundColor")
        fun changePreferredBackgroundColor(root: View, bgOption: BackgroundOption) {
            when(bgOption) {
                BackgroundOption.BgLight -> {
                    root.setBackgroundColor(ResourcesCompat.getColor(root.resources, R.color.white, null))
                }
                BackgroundOption.BgDark -> {
                    root.setBackgroundColor(ResourcesCompat.getColor(root.resources, R.color.gray, null))
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
    }
}