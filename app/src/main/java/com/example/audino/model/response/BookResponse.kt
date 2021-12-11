package com.example.audino.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "saved_books_table")
data class BookResponse(
    val bookId: String?,
    val bookName: String? = null,
    val authorName: String? = null,
    val thumbnailUrl: String? = null,
    val audioUrl: String? = null,
    @SerializedName("Description") val description: String? = null,
    @SerializedName("SummaryText") val summary: String? = null
) : Serializable {

    @PrimaryKey(autoGenerate = false)
    var savedBookId: String = bookId ?: "bookId_$bookId"

}
