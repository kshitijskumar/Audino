package com.example.audino.model.repositories

import android.util.Log
import com.example.audino.model.api.ApiService
import com.example.audino.model.response.AllBooksResponse
import com.example.audino.model.response.BookResponse
import com.example.audino.model.response.BookSummaryResponse
import com.example.audino.model.response.GenreResponse
import com.example.audino.utils.Injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.logging.Logger

class MainRepositoryImpl(
    private val api: ApiService = Injector.getInjector().provideApiService()
) : MainRepository {

    companion object {
        val bookIdAndBookResponseMap = mutableMapOf<String, BookResponse>()
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
        //returning dummy for now
        return BookSummaryResponse(
            bookId,
            bookIdAndBookResponseMap[bookId]?.bookName ?: "Some book",
            bookIdAndBookResponseMap[bookId]?.authorName ?: "Some author",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Eu scelerisque felis imperdiet proin. Pharetra sit amet aliquam id diam. Odio aenean sed adipiscing diam. Nunc non blandit massa enim nec. At tempor commodo ullamcorper a lacus vestibulum sed. Nunc mattis enim ut tellus elementum sagittis vitae et leo. Volutpat est velit egestas dui id ornare. Senectus et netus et malesuada fames ac turpis egestas. Felis eget velit aliquet sagittis id consectetur purus. Tortor dignissim convallis aenean et tortor. Lacus laoreet non curabitur gravida arcu ac tortor dignissim. Convallis tellus id interdum velit laoreet id. Quis imperdiet massa tincidunt nunc pulvinar sapien et. Fringilla ut morbi tincidunt augue. Sit amet commodo nulla facilisi nullam vehicula ipsum a arcu. Mus mauris vitae ultricies leo. Id velit ut tortor pretium viverra suspendisse potenti.\n" +
                    "\n" +
                    "Porta non pulvinar neque laoreet suspendisse interdum consectetur libero. Sed odio morbi quis commodo odio aenean sed. Blandit cursus risus at ultrices mi tempus. Nibh sit amet commodo nulla facilisi nullam. Bibendum ut tristique et egestas quis ipsum suspendisse. Sed lectus vestibulum mattis ullamcorper velit sed. Neque egestas congue quisque egestas diam in arcu. Ut pharetra sit amet aliquam id diam maecenas ultricies mi. Tellus in hac habitasse platea dictumst vestibulum rhoncus est pellentesque. In massa tempor nec feugiat nisl. Morbi non arcu risus quis. Nunc lobortis mattis aliquam faucibus purus in. Consectetur purus ut faucibus pulvinar elementum integer enim neque volutpat. Eget felis eget nunc lobortis mattis aliquam faucibus purus. Ridiculus mus mauris vitae ultricies leo integer. Ipsum nunc aliquet bibendum enim. Lectus arcu bibendum at varius vel pharetra. Neque convallis a cras semper auctor neque. Donec enim diam vulputate ut pharetra sit amet. Ornare aenean euismod elementum nisi.\n" +
                    "\n" +
                    "Nulla facilisi etiam dignissim diam quis. Faucibus pulvinar elementum integer enim neque. Ut sem nulla pharetra diam sit. Vestibulum lectus mauris ultrices eros in cursus turpis. Aliquet nibh praesent tristique magna. Adipiscing at in tellus integer feugiat scelerisque varius morbi enim. Felis eget nunc lobortis mattis aliquam faucibus. Tortor posuere ac ut consequat semper viverra nam libero. Neque egestas congue quisque egestas diam in. Ridiculus mus mauris vitae ultricies leo. Euismod quis viverra nibh cras pulvinar mattis nunc. Volutpat diam ut venenatis tellus. Pretium fusce id velit ut tortor pretium viverra. Turpis in eu mi bibendum neque egestas congue quisque egestas. Tristique senectus et netus et. Purus sit amet luctus venenatis lectus magna. Massa eget egestas purus viverra accumsan in nisl nisi. Commodo quis imperdiet massa tincidunt nunc pulvinar sapien et ligula. Ullamcorper velit sed ullamcorper morbi tincidunt ornare massa.\n" +
                    "\n" +
                    "Magna ac placerat vestibulum lectus mauris ultrices eros in cursus. Neque volutpat ac tincidunt vitae semper. In mollis nunc sed id semper risus in hendrerit gravida. Neque convallis a cras semper auctor neque vitae tempus quam. In aliquam sem fringilla ut morbi tincidunt augue. Lectus quam id leo in vitae turpis. Nec ultrices dui sapien eget. Nulla aliquet enim tortor at. Amet consectetur adipiscing elit duis. Eu tincidunt tortor aliquam nulla facilisi cras fermentum odio. Congue quisque egestas diam in arcu cursus. Commodo quis imperdiet massa tincidunt nunc pulvinar sapien et ligula. Sollicitudin ac orci phasellus egestas tellus rutrum tellus. Urna molestie at elementum eu facilisis sed. Non nisi est sit amet facilisis magna.\n" +
                    "\n" +
                    "Consequat interdum varius sit amet mattis vulputate enim. Libero nunc consequat interdum varius. Eu ultrices vitae auctor eu augue ut. Vitae congue eu consequat ac. Sed velit dignissim sodales ut. Suspendisse ultrices gravida dictum fusce ut placerat orci nulla. Fermentum odio eu feugiat pretium nibh ipsum. Et netus et malesuada fames ac turpis egestas integer. Eleifend mi in nulla posuere sollicitudin aliquam ultrices. Nunc mattis enim ut tellus elementum sagittis vitae et."
        )
    }
}