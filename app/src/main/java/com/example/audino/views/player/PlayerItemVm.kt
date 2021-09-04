package com.example.audino.views.player

import androidx.databinding.ObservableField
import com.example.audino.model.response.BookResponse

class PlayerItemVm() {

    val book = ObservableField<BookResponse>()

    fun initBook(bookResponse: BookResponse) {
        book.set(bookResponse)
    }

}