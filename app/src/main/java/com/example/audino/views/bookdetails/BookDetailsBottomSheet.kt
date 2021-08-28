package com.example.audino.views.bookdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.audino.databinding.FragmentBookDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BookDetailsBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBookDetailsBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookDetailsBottomSheetBinding.inflate(inflater)
        return binding.root
    }
}