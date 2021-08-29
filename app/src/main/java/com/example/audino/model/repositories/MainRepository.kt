package com.example.audino.model.repositories

import com.example.audino.model.response.AllBooksResponse

interface MainRepository {

    suspend fun getAllBooks() : AllBooksResponse
}