package com.example.audino.views.bookdetails

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.audino.R
import com.example.audino.databinding.FragmentBookDetailsBottomSheetBinding
import com.example.audino.model.response.BookResponse
import com.example.audino.utils.Utils
import com.example.audino.views.callbacks.SwitchFragmentCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BookDetailsBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBookDetailsBottomSheetBinding

    private lateinit var bookVm: BookDetailsVm

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookDetailsBottomSheetBinding.inflate(inflater)
        if (::bookVm.isInitialized) binding.vm = bookVm
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NORMAL, R.style.RoundedBottomSheetStyle)
        return super.onCreateDialog(savedInstanceState)
    }

    fun setBookDetails(book: BookResponse) {
        bookVm = BookDetailsVm(book).apply {
            setOnPlayClickListener(object : OnPlayClickFromDetails {
                override fun onPlayClick(book: BookResponse) {
                    dismiss()
                    Log.d("ClickEvent", "in bottom sheet")
                    (requireContext() as SwitchFragmentCallback).openPlayerFragment(book)
                }

                override fun onShareBookClick(book: BookResponse) {
                    Utils.shareBook(requireContext(), book)
                }
            })
        }
    }

    companion object {
        const val TAG = "BookDetailsBottomSheet"
        fun newInstance() = BookDetailsBottomSheet()
    }

    interface OnPlayClickFromDetails {
        fun onPlayClick(book: BookResponse)
        fun onShareBookClick(book: BookResponse)
    }

}