package com.example.audino.views.player

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.audino.databinding.FragmentPlayerBinding
import com.example.audino.model.response.BookResponse

class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding

    private val playerItemVm = PlayerItemVm()

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
        fun newInstance(bundle: Bundle?) : PlayerFragment {
            return PlayerFragment().apply {
                arguments = bundle
            }
        }
    }
}