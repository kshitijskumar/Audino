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

//    override suspend fun getAllBooks(): AllBooksResponse {
//        //Todo: Dummy response for now
//        delay(1500L)
//        return AllBooksResponse(
//            genres = listOf(
//                GenreResponse(
//                    "gen1",
//                    "Fantasy",
//                    listOf(
//                        BookResponse(
//                            "gen1_book1",
//                            "Atomic Habits",
//                            "James Clear",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/audino-327610.appspot.com/o/84%2Faudio_20.mp3?alt=media",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        ),
//                        BookResponse(
//                            "gen1_book2",
//                            "Book 2",
//                            "Author 2",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        ),
//                        BookResponse(
//                            "gen1_book3",
//                            "Book 1",
//                            "Author 1",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        ),
//                        BookResponse(
//                            "gen1_book4",
//                            "Book 1",
//                            "Author 1",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        )
//                    )
//                ),
//                GenreResponse(
//                    "gen2",
//                    "Adventure",
//                    listOf(
//                        BookResponse(
//                            "gen2_book1",
//                            "Book 1",
//                            "Author 1",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Description 1",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        ),
//                        BookResponse(
//                            "gen2_book2",
//                            "Book 2",
//                            "Author 2",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Description 1",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        ),
//                        BookResponse(
//                            "gen2_book3",
//                            "Book 1",
//                            "Author 1",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Description 1",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        ),
//                        BookResponse(
//                            "gen2_book4",
//                            "Book 1",
//                            "Author 1",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Description 1",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        )
//                    )
//                ),
//                GenreResponse(
//                    "gen3",
//                    "Thriller",
//                    listOf(
//                        BookResponse(
//                            "gen3_book1",
//                            "Book 1",
//                            "Author 1",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Description 1",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        ),
//                        BookResponse(
//                            "gen3_book2",
//                            "Book 2",
//                            "Author 2",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Description 1",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        ),
//                        BookResponse(
//                            "gen3_book3",
//                            "Book 1",
//                            "Author 1",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Description 1",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        ),
//                        BookResponse(
//                            "gen3_book4",
//                            "Book 1",
//                            "Author 1",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Description 1",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        ),
//                        BookResponse(
//                            "gen3_book5",
//                            "Book 1",
//                            "Author 1",
//                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
//                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
//                            "Description 1",
//                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
//                        )
//                    )
//                )
//            )
//        )
//    }

    override suspend fun getAllBooks(): AllBooksResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.fetchAllBooks()
                if (response.isSuccessful && response.body() != null) {
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

    override suspend fun getBookSummaryContent(bookId: String): BookSummaryResponse {
        //returning dummy for now
        return BookSummaryResponse(
            "gen3_book5",
            "Atomic Habits",
            "James Clear $bookId",
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
        )
    }
}