package com.example.audino.views.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.audino.R
import com.example.audino.databinding.FragmentReadSectionEditBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReadSectionEditBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentReadSectionEditBottomSheetBinding

    private lateinit var readModeListener: ReadModeChangeListener

    private val editVm by lazy {
        SectionEditVm()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundedBottomSheetStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadSectionEditBottomSheetBinding.inflate(inflater)
        binding.vm = editVm
        return binding.root
    }

    fun setOnReadModeChangeListener(listener: ReadModeChangeListener) {
        readModeListener = listener
        editVm.setReadModeChangeListener(readModeListener)
    }


    companion object {
        @JvmStatic
        fun newInstance() = ReadSectionEditBottomSheetFragment()
    }

    interface ReadModeChangeListener {
        fun onTextSizeChanged(textSizeOption: TextSizeOptions)
        fun onBackgroundChanged(bgOption: BackgroundOption)
    }
}