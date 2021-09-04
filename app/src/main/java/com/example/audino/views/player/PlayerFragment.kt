package com.example.audino.views.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.audino.databinding.FragmentPlayerBinding
import com.example.audino.model.response.BookResponse
import com.example.audino.utils.Injector
import com.example.audino.viewmodels.MainViewModel

class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding

    private val mainViewModel by lazy {
        MainViewModel.getMainViewModel(requireActivity(), Injector.getInjector().provideAudinoServiceConnection(requireContext()))
    }

    private val playerItemVm = PlayerItemVm(object : PlayerFragmentClickCallback {
        override fun onCloseClick() {
            activity?.supportFragmentManager?.popBackStack()
        }

        override fun playBook() {
            mainViewModel.playBookFromStart(arguments?.getSerializable("Book") as BookResponse)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayerBinding.inflate(inflater)
        binding.vm = playerItemVm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerItemVm.initBook(arguments?.getSerializable("Book") as BookResponse)
    }

    companion object {
        fun newInstance() = PlayerFragment()
        fun newInstance(bundle: Bundle?): PlayerFragment {
            return PlayerFragment().apply {
                arguments = bundle
            }
        }
    }

    interface PlayerFragmentClickCallback {
        fun onCloseClick()
        fun playBook()
    }
}