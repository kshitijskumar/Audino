package com.example.audino.views.callbacks

import com.example.audino.model.response.BookResponse

interface SwitchFragmentCallback {

    fun openPlayerFragment(book: BookResponse)

    fun openReadFragment(bookId: String)

    fun openLibraryFragment()
}