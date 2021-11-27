package com.example.audino.model.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("_id") val genreId: String?,
    @SerializedName("genreName") val genreName: String? = null,
    @SerializedName("books") val books: List<BookResponse> = listOf()
)