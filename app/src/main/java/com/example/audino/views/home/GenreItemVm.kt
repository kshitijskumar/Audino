package com.example.audino.views.home

import androidx.databinding.ObservableField
import com.example.audino.model.response.GenreResponse
import java.util.*

class GenreItemVm {

    val genreName = ObservableField("")
    lateinit var genreId: String

    fun initData(genreRes: GenreResponse) {
        genreName.set(genreRes.genreName)
        genreId = genreRes.genreId ?: ""
    }
}