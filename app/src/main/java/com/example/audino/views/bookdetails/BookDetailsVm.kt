package com.example.audino.views.bookdetails

import androidx.databinding.ObservableField
import com.example.audino.model.response.BookResponse

class BookDetailsVm(private val book: BookResponse) {

    val bookDetails = ObservableField<BookResponse>(book)
    private var onPlayClickListener: BookDetailsBottomSheet.OnPlayClickFromDetails? = null

    fun setOnPlayClickListener(listener: BookDetailsBottomSheet.OnPlayClickFromDetails) {
        onPlayClickListener = listener
    }

    fun onPlayClick() {
        bookDetails.get()?.let { onPlayClickListener?.onPlayClick(it) }
    }

}