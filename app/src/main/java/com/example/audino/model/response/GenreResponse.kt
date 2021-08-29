package com.example.audino.model.response

data class GenreResponse(
    val genreId: String?,
    val genreName: String? = null,
    val books: List<BookResponse> = listOf()
)
