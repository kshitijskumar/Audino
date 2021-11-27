package com.example.audino.views.home

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.audino.databinding.FragmentHomeBinding
import com.example.audino.model.repositories.MainRepositoryImpl
import com.example.audino.model.response.BookResponse
import com.example.audino.utils.Injector
import com.example.audino.viewmodels.MainViewModel
import com.example.audino.views.adapters.GenreListAdapter
import com.example.audino.views.bookdetails.BookDetailsBottomSheet
import com.example.audino.views.callbacks.SwitchFragmentCallback

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var genreListAdapter: GenreListAdapter

    private val mainViewModel by lazy {
        MainViewModel.getMainViewModel(requireActivity(), Injector.getInjector().provideAudinoServiceConnection(requireContext()))
    }

    var bookIdFromDeeplink: String? = null
    var isDeeplinkFlowHandled: Boolean = false

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

        initViews()
        initRecyclerView()
        observeValues()
    }

    private fun initViews() {
        binding.layoutNowPlaying.root.setOnClickListener {
            val currBookMetaDataCompat = mainViewModel.currBook.value
            currBookMetaDataCompat?.let { metaData ->
                val bookId = metaData.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
                val book = MainRepositoryImpl.bookIdAndBookResponseMap[bookId]
                book?.let {
                    (requireContext() as SwitchFragmentCallback).openPlayerFragment(book)
                }
            }
        }
    }

    private fun initRecyclerView() {
        genreListAdapter = GenreListAdapter(Injector.getInjector().provideAudinoServiceConnection(requireContext()))
        genreListAdapter.setOnGenreClickListener(object : GenreListAdapter.OnGenreItemClick {
            override fun onSeeMoreClick(genreId: String) {
                Toast.makeText(requireContext(), "see more clicked for: $genreId", Toast.LENGTH_SHORT).show()
            }

            override fun onBookClicked(book: BookResponse) {
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
            handleDeeplinkFlowIfAny()
            Log.d("GenreList", "$it")
        }

        mainViewModel.currBook.observe(viewLifecycleOwner) {
            binding.layoutNowPlaying.root.visibility = if (it == null) View.GONE else View.VISIBLE
            binding.layoutNowPlaying.tvTitle.text = it?.getString(MediaMetadataCompat.METADATA_KEY_TITLE)
        }
    }

    private fun handleDeeplinkFlowIfAny() {
        if (bookIdFromDeeplink != null && !isDeeplinkFlowHandled) {
            isDeeplinkFlowHandled = true
            if (MainRepositoryImpl.bookIdAndBookResponseMap.containsKey(bookIdFromDeeplink)) {
                MainRepositoryImpl.bookIdAndBookResponseMap[bookIdFromDeeplink]?.let { book ->
                    BookDetailsBottomSheet.newInstance().apply {
                        setBookDetails(book)
                        show(this@HomeFragment.childFragmentManager, BookDetailsBottomSheet.TAG)
                    }
                }
            }
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