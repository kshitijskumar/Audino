package com.example.audino.views.bookdetails

import androidx.databinding.ObservableField
import com.example.audino.model.response.BookResponse

class BookDetailsVm(private val book: BookResponse) {

    val bookDetails = ObservableField<BookResponse>(book)

}