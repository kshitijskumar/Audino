package com.example.audino.utils

import android.content.Context
import android.content.Intent
import com.example.audino.model.response.BookResponse

object Utils {

    fun shareBook(context: Context, bookResponse: BookResponse) {
        val template = Constants.SHARE_BOOK_TEMPLATE
        val updatedTemplate = template
            .replace("{{BOOK_NAME}}", bookResponse.bookName.toString())
            .replace("{{AUTHOR}}", bookResponse.authorName.toString())
            .replace("{{BOOK_ID}}", bookResponse.bookId.toString())

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, updatedTemplate)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Hey Reader!")
        context.startActivity(shareIntent)
    }


}