package com.example.audino.views.adapters

import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.audino.databinding.LayoutCategoryListBinding
import com.example.audino.model.response.BookResponse
import com.example.audino.model.response.GenreResponse
import com.example.audino.player.extensionfunctions.toBookResponse
import com.example.audino.service.AudinoServiceConnection

class GenreListAdapter(private val serviceConnection: AudinoServiceConnection) : ListAdapter<GenreResponse, GenreListAdapter.GenreViewHolder>(diffUtil) {

    private val booksListGenreIdMap = mutableMapOf<String, List<BookResponse>>()
    private val booksIdAdapterMap = mutableMapOf<String, BooksAdapter>()

    private var genreItemClickListener: OnGenreItemClick? = null

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<GenreResponse>() {
            override fun areItemsTheSame(oldItem: GenreResponse, newItem: GenreResponse): Boolean {
                return oldItem.genreId == newItem.genreId
            }

            override fun areContentsTheSame(
                oldItem: GenreResponse,
                newItem: GenreResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GenreViewHolder(LayoutCategoryListBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun setOnGenreClickListener(listener: OnGenreItemClick) {
        genreItemClickListener = listener
    }

    interface OnGenreItemClick {
        fun onSeeMoreClick(genreId: String)
        fun onBookClicked(book: BookResponse)
    }

    inner class GenreViewHolder(private val binding: LayoutCategoryListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(genre: GenreResponse) {
            binding.tvGenre.text = genre.genreName

            binding.rvBooks.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
            val adapter = if (booksIdAdapterMap.containsKey(genre.genreId)) {
                booksIdAdapterMap[genre.genreId]
            } else {
                Log.d("GenreList", "creating adapter")
                val bookAdapter = BooksAdapter(true)
                bookAdapter.setOnBookClickListener(object : BooksAdapter.OnBookClick {
                    override fun onBookClick(book: BookResponse) {
                        genreItemClickListener?.onBookClicked(book)
                    }
                })
                booksIdAdapterMap[genre.genreId!!] = bookAdapter
                bookAdapter
            }
            binding.rvBooks.adapter = adapter

            val booksList = if (booksListGenreIdMap.containsKey(genre.genreId)) {
                booksListGenreIdMap[genre.genreId]
            } else {
                subscribeToGetBooksList(genre.genreId!!)
                //send empty list for starters, then when onChildren loaded is called, we update the values
                listOf()
            }

            adapter?.submitList(booksList)


        }

        private fun subscribeToGetBooksList(parentId: String) {
            serviceConnection.subscribe(parentId, object : MediaBrowserCompat.SubscriptionCallback() {
                override fun onChildrenLoaded(
                    parentId: String,
                    children: MutableList<MediaBrowserCompat.MediaItem>
                ) {
                    super.onChildrenLoaded(parentId, children)
                    //update the map
                    booksListGenreIdMap[parentId] = children.map {
                        it.toBookResponse()
                    }
                    booksIdAdapterMap[parentId]?.submitList(booksListGenreIdMap[parentId])
                }
            })
        }
    }
}