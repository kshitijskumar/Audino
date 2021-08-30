package com.example.audino.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.audino.databinding.LayoutBookBinding
import com.example.audino.databinding.LayoutGridBookBinding
import com.example.audino.model.response.BookResponse
import com.example.audino.views.home.LinearBookItemVm
import java.lang.IllegalStateException


class BooksAdapter(private val isLinear: Boolean = true) :  ListAdapter<BookResponse, RecyclerView.ViewHolder>(
    diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BookResponse>() {
            override fun areItemsTheSame(oldItem: BookResponse, newItem: BookResponse): Boolean {
                return oldItem.bookId == newItem.bookId
            }

            override fun areContentsTheSame(oldItem: BookResponse, newItem: BookResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (isLinear) {
            LinearBooksViewHolder(LayoutBookBinding.inflate(inflater, parent, false))
        } else {
            GridBooksViewHolder(LayoutGridBookBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val book = getItem(position)
        when(holder) {
            is LinearBooksViewHolder -> holder.onBind(book)
            is GridBooksViewHolder ->  Unit  //TODO
            else -> throw IllegalStateException("No valid Viewholder")
        }
    }

    inner class LinearBooksViewHolder(private val binding: LayoutBookBinding) : RecyclerView.ViewHolder(binding.root) {
        private val itemVm = LinearBookItemVm()

        fun onBind(book: BookResponse) {
            Log.d("GenreList", "onBind for books")
            itemVm.initData(book)
        }

        init {
            binding.vm = itemVm
        }

    }

    inner class GridBooksViewHolder(private val binding: LayoutGridBookBinding) : RecyclerView.ViewHolder(binding.root)
}