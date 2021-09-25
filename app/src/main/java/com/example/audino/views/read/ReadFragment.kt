package com.example.audino.views.read

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.audino.databinding.FragmentReadBinding
import com.example.audino.utils.Injector
import com.example.audino.viewmodels.MainViewModel

class ReadFragment : Fragment() {

    private lateinit var binding: FragmentReadBinding

    private val mainViewModel by lazy {
        MainViewModel.getMainViewModel(requireActivity(), Injector.getInjector().provideAudinoServiceConnection(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainVm = mainViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getString(BOOK_ID) != mainViewModel.currBookSummary.value?.bookId ?: true) {
            mainViewModel.getSummaryOfCurrentBook(arguments?.getString(BOOK_ID).toString())
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