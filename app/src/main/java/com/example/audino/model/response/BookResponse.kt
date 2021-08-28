package com.example.audino.model.response

data class BookResponse(
    val bookId: String,
    val bookName: String? = null,
    val authorName: String? = null,
    val thumbnailUrl: String? = null,
    val audioUrl: String? = null,
    val description: String? = null,
    val summary: String? = null
)
