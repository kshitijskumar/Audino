package com.example.audino.views.read

import androidx.databinding.ObservableField
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


}

enum class TextSizeOptions {
    TextSmall,TextMedium, TextLarge
}

enum class BackgroundOption {
    BgLight, BgDark
}