package com.example.audino.views.callbacks

import com.example.audino.model.response.BookResponse

interface SwitchFragmentCallback {

    fun openPlayerFragment(book: BookResponse)
}