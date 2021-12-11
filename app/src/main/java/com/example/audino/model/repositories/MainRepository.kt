package com.example.audino.model.repositories

import com.example.audino.model.response.AllBooksResponse
import com.example.audino.model.response.BookResponse
import com.example.audino.model.response.BookSummaryResponse

interface MainRepository {

    suspend fun getAllBooks() : AllBooksResponse

    suspend fun getBookSummaryContent(bookId: String) : BookSummaryResponse

    suspend fun saveBookInDb(book: BookResponse)

    suspend fun unsaveBookFromDb(book: BookResponse)
}