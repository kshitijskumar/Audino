package com.example.audino.views.home

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.bumptech.glide.Glide
import com.example.audino.model.response.BookResponse

class LinearBookItemVm {

    val title = ObservableField("")
    val authorName = ObservableField("")
    val thumbnailUrl = ObservableField("")
    val summary = ObservableField("")
    val description = ObservableField("")

    private lateinit var book: BookResponse

    fun initData(book: BookResponse) {
        this.book = book
        Log.d("GenreList", "initForBooks: $book")
        title.set(book.bookName)
        authorName.set(book.authorName)
        thumbnailUrl.set(book.thumbnailUrl)
        summary.set(book.summary)
        description.set(book.description)
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