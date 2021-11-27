package com.example.audino.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BookResponse(
    val bookId: String?,
    val bookName: String? = null,
    val authorName: String? = null,
    val thumbnailUrl: String? = null,
    val audioUrl: String? = null,
    @SerializedName("Description") val description: String? = null,
    @SerializedName("SummaryText") val summary: String? = null
) : Serializable
