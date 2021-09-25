package com.example.audino.views.read

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.audino.databinding.FragmentReadBinding
import com.example.audino.utils.Injector
import com.example.audino.viewmodels.MainViewModel
import com.example.audino.views.read.ReadSectionEditBottomSheetFragment.*

class ReadFragment : Fragment() {

    private lateinit var binding: FragmentReadBinding

    private val mainViewModel by lazy {
        MainViewModel.getMainViewModel(requireActivity(), Injector.getInjector().provideAudinoServiceConnection(requireContext()))
    }

    private val readSectionVm by lazy {
        ReadSectionVm()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainVm = mainViewModel
        binding.vm = readSectionVm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getString(BOOK_ID) != mainViewModel.currBookSummary.value?.bookId ?: true) {
            mainViewModel.getSummaryOfCurrentBook(arguments?.getString(BOOK_ID).toString())
        }

        binding.btnEditSection.setOnClickListener {
            val  editBottomSheet = ReadSectionEditBottomSheetFragment.newInstance()
            editBottomSheet.setOnReadModeChangeListener(object : ReadModeChangeListener {
                override fun onTextSizeChanged(textSizeOption: TextSizeOptions) {
                    readSectionVm.updateTextSizeOption(textSizeOption)
                }

                override fun onBackgroundChanged(bgOption: BackgroundOption) {
                    readSectionVm.updateBgOption(bgOption)
                }
            })
            editBottomSheet.show(childFragmentManager, ReadSectionEditBottomSheetFragment::class.java.name)
        }

    }

    companion object {

        const val BOOK_ID = "book_id"

        @JvmStatic
        fun newInstance() = ReadFragment()

        @JvmStatic
        fun newInstance(bundle: Bundle) : ReadFragment {
            val rf = ReadFragment().apply {
                arguments = bundle
            }

            return rf
        }
    }
}