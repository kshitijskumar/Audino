package com.example.audino.model.repositories

import android.util.Log
import com.example.audino.model.api.ApiService
import com.example.audino.model.dao.SavedBooksDao
import com.example.audino.model.response.AllBooksResponse
import com.example.audino.model.response.BookResponse
import com.example.audino.model.response.BookSummaryResponse
import com.example.audino.utils.Injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainRepositoryImpl(
    private val api: ApiService = Injector.getInjector().provideApiService(),
    private val dao: SavedBooksDao = Injector.getInjector().providesSaveBooksDao()
) : MainRepository {

    companion object {
        val bookIdAndBookResponseMap = mutableMapOf<String, BookResponse>()
        val bookIdAndSummaryText = mutableMapOf<String, String>()
    }

    override suspend fun getAllBooks(): AllBooksResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.fetchAllBooks()
                if (response.isSuccessful && response.body() != null) {
                    Log.d("DeeplinkStuff", "caching start")
                    cacheAllBooksResponse(response.body()!!)
                    Log.d("DeeplinkStuff", "$bookIdAndBookResponseMap")
                    Log.d("DeeplinkStuff", "sending")
                    response.body()!!
                } else {
                    AllBooksResponse()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                AllBooksResponse()
            }
        }
    }

    private suspend fun cacheAllBooksResponse(allBooksResponse: AllBooksResponse) {
        withContext(Dispatchers.Default) {
            allBooksResponse.genres.forEach { genre ->
                genre.books.forEach { book ->
                    bookIdAndBookResponseMap[book.bookId ?: ""] = book
                }
            }
        }
    }


    override suspend fun getBookSummaryContent(bookId: String): BookSummaryResponse {
        return withContext(Dispatchers.IO) {
            val bookSummary = BookSummaryResponse(
                bookIdAndBookResponseMap[bookId]?.bookId.toString(),
                bookIdAndBookResponseMap[bookId]?.bookName.toString(),
                bookIdAndBookResponseMap[bookId]?.authorName
            )
            val summary = try {
                if (bookIdAndSummaryText[bookId] != null) {
                    bookIdAndSummaryText[bookId]
                } else {
                    val response = api.fetchSummaryForBookId(bookIdAndBookResponseMap[bookId]?.summary ?: "")
                    if (response.isSuccessful && response.body() != null) {
                        bookIdAndSummaryText[bookId] = response.body()?.summary ?: ""
                        response.body()?.summary ?: ""
                    } else {
                        ""
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
            Log.d("SummaryStuff", "$bookIdAndSummaryText")
            bookSummary.copy(
                summary = summary
            )
        }
    }

    override suspend fun saveBookInDb(book: BookResponse) {
        withContext(Dispatchers.IO) {
            dao.saveBookInDb(book)
        }
    }

    override suspend fun unsaveBookFromDb(book: BookResponse) {
        withContext(Dispatchers.IO) {
            dao.unsaveBookFromDb(book)
        }
    }
}