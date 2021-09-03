package com.example.audino.views.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.audino.databinding.FragmentHomeBinding
import com.example.audino.model.response.BookResponse
import com.example.audino.utils.Injector
import com.example.audino.viewmodels.MainViewModel
import com.example.audino.views.adapters.GenreListAdapter
import com.example.audino.views.bookdetails.BookDetailsBottomSheet

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var genreListAdapter: GenreListAdapter

    private val mainViewModel by lazy {
        MainViewModel.getMainViewModel(requireActivity(), Injector.getInjector().provideAudinoServiceConnection(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: remove this
        binding.tvGreet.text = "Hey Kshitij!"

        initRecyclerView()
        observeValues()
    }

    private fun initRecyclerView() {
        genreListAdapter = GenreListAdapter(Injector.getInjector().provideAudinoServiceConnection(requireContext()))
        genreListAdapter.setOnGenreClickListener(object : GenreListAdapter.OnGenreItemClick {
            override fun onSeeMoreClick(genreId: String) {
                Toast.makeText(requireContext(), "see more clicked for: $genreId", Toast.LENGTH_SHORT).show()
            }

            override fun onBookClicked(book: BookResponse) {
                Toast.makeText(requireContext(), "book id is: ${book.bookId}", Toast.LENGTH_SHORT).show()
                BookDetailsBottomSheet.newInstance().apply {
                    setBookDetails(book)
                    show(this@HomeFragment.childFragmentManager, BookDetailsBottomSheet.TAG)
                }
            }
        })
        binding.rvBooks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = genreListAdapter
        }
    }

    private fun observeValues() {
        mainViewModel.genresList.observe(viewLifecycleOwner) {
            genreListAdapter.submitList(it)
            Log.d("GenreList", "$it")
        }
    }

    companion object {
        fun newInstance(bundle: Bundle?): HomeFragment {
            return HomeFragment().apply {
                arguments = bundle
            }
        }
        const val TAG = "HOME_FRAGMENT"
    }
}