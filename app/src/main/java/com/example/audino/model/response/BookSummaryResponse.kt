package com.example.audino.model.response

data class BookSummaryResponse(
    val bookId: String? = null,
    val bookTitle: String? = null,
    val authorName: String? = null,
    val summary: String? = null
)

data class SummaryOnlyResponse(
    val summary: String? = null
)
