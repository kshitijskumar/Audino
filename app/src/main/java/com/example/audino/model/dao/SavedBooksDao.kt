package com.example.audino.model.dao

import androidx.room.*
import com.example.audino.model.response.BookResponse

@Dao
interface SavedBooksDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveBookInDb(book: BookResponse)

    @Delete
    suspend fun unsaveBookFromDb(book: BookResponse)

    @Query("SELECT * FROM saved_books_table")
    suspend fun getAllSavedBooks() : List<BookResponse>

    @Query("SELECT COUNT() FROM saved_books_table WHERE savedBookId = :id")
    suspend fun isBookSaved(id: String) : Int

}