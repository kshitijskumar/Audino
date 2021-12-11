package com.example.audino.views.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.audino.databinding.FragmentLibraryBinding
import com.example.audino.model.response.BookResponse
import com.example.audino.utils.Injector
import com.example.audino.viewmodels.MainViewModel
import com.example.audino.views.adapters.BooksAdapter
import com.example.audino.views.callbacks.SwitchFragmentCallback

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding: FragmentLibraryBinding get() = _binding!!

    private lateinit var adapter: BooksAdapter

    private val mainViewModel by lazy {
        MainViewModel.getMainViewModel(requireActivity(), Injector.getInjector().provideAudinoServiceConnection(requireContext()))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLibraryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeValues()
        mainViewModel.getAllSavedBooks()
    }

    private fun observeValues() {
        mainViewModel.savedBooks.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupViews() {
        adapter = BooksAdapter(false).apply {
            this.setOnBookClickListener(object : BooksAdapter.OnBookClick {
                override fun onBookClick(book: BookResponse) {
                    (requireContext() as SwitchFragmentCallback).openPlayerFragment(book)
                }
            })
        }
        binding.rvSavedBooks.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@LibraryFragment.adapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = LibraryFragment()
    }
}