package com.example.audino.views.read

import androidx.databinding.ObservableField
import com.example.audino.utils.Constants
import com.example.audino.utils.Injector
import com.example.audino.views.read.ReadSectionEditBottomSheetFragment.*

class SectionEditVm {

    val textSizeOptions = ObservableField(TextSizeOptions.TextSmall)
    val bgOptions = ObservableField(BackgroundOption.BgLight)

    private var readModeChangeListener: ReadModeChangeListener? = null

    fun setReadModeChangeListener(listener: ReadModeChangeListener) {
        readModeChangeListener = listener
    }

    fun textSizeOptionToggle(textOption: TextSizeOptions) {
        textSizeOptions.set(textOption)
        readModeChangeListener?.onTextSizeChanged(textOption)
    }

    fun backgroundOptionToggle(bgOption: BackgroundOption) {
        bgOptions.set(bgOption)
        readModeChangeListener?.onBackgroundChanged(bgOption)
    }

    init {
        val pref = Injector.getInjector().provideSharedPrefs()
        val textSize = pref.getInt(Constants.PREFERRED_TEXT_SIZE, 0)
        val bg = pref.getInt(Constants.PREFERRED_BG_COLOR, 0)
        textSizeOptions.set(integerToTextOption(textSize))
        bgOptions.set(integerToBgOption(bg))
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
}

enum class TextSizeOptions {
    TextSmall,TextMedium, TextLarge
}

enum class BackgroundOption {
    BgLight, BgDark
}