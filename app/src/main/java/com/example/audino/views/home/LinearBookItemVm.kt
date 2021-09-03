package com.example.audino.views.home

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.bumptech.glide.Glide
import com.example.audino.model.response.BookResponse
import com.example.audino.views.adapters.BooksAdapter

class LinearBookItemVm() {

    val title = ObservableField("")
    val authorName = ObservableField("")
    val thumbnailUrl = ObservableField("")
    val summary = ObservableField("")
    val description = ObservableField("")

    private var onBookClickListener: BooksAdapter.OnBookClick? = null

    private lateinit var book: BookResponse

    fun initData(book: BookResponse) {
        this.book = book
        title.set(book.bookName)
        authorName.set(book.authorName)
        thumbnailUrl.set(book.thumbnailUrl)
        summary.set(book.summary)
        description.set(book.description)
    }

    fun setOnBookClick(listener: BooksAdapter.OnBookClick) {
        onBookClickListener = listener
    }

    fun onBookClicked() {
        onBookClickListener?.onBookClick(book)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadFromUrl")
        fun loadImgFromUrl(iv: ImageView, url: String?) {
            Glide.with(iv.context)
                .load(url)
                .into(iv)
        }
    }
}