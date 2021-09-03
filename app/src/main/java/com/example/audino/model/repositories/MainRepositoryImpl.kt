package com.example.audino.model.repositories

import com.example.audino.model.response.AllBooksResponse
import com.example.audino.model.response.BookResponse
import com.example.audino.model.response.GenreResponse
import kotlinx.coroutines.delay

class MainRepositoryImpl : MainRepository {

    override suspend fun getAllBooks(): AllBooksResponse {
        //Todo: Dummy response for now
        delay(1500L)
        return AllBooksResponse(
            genres = listOf(
                GenreResponse(
                    "gen1",
                    "Fantasy",
                    listOf(
                        BookResponse(
                            "gen1_book1",
                            "Book 1",
                            "Author 1",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        ),
                        BookResponse(
                            "gen1_book2",
                            "Book 2",
                            "Author 2",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        ),
                        BookResponse(
                            "gen1_book3",
                            "Book 1",
                            "Author 1",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        ),
                        BookResponse(
                            "gen1_book4",
                            "Book 1",
                            "Author 1",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        )
                    )
                ),
                GenreResponse(
                    "gen2",
                    "Adventure",
                    listOf(
                        BookResponse(
                            "gen2_book1",
                            "Book 1",
                            "Author 1",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Description 1",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        ),
                        BookResponse(
                            "gen2_book2",
                            "Book 2",
                            "Author 2",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Description 1",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        ),
                        BookResponse(
                            "gen2_book3",
                            "Book 1",
                            "Author 1",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Description 1",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        ),
                        BookResponse(
                            "gen2_book4",
                            "Book 1",
                            "Author 1",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Description 1",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        )
                    )
                ),
                GenreResponse(
                    "gen3",
                    "Thriller",
                    listOf(
                        BookResponse(
                            "gen3_book1",
                            "Book 1",
                            "Author 1",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Description 1",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        ),
                        BookResponse(
                            "gen3_book2",
                            "Book 2",
                            "Author 2",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Description 1",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        ),
                        BookResponse(
                            "gen3_book3",
                            "Book 1",
                            "Author 1",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Description 1",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        ),
                        BookResponse(
                            "gen3_book4",
                            "Book 1",
                            "Author 1",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Description 1",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        ),
                        BookResponse(
                            "gen3_book5",
                            "Book 1",
                            "Author 1",
                            "https://firebasestorage.googleapis.com/v0/b/horizon-e926a.appspot.com/o/EtsDFIHIODX3yrzWYfx4viq6lgc2_1608738462477.jpg?alt=media&token=cbd7539a-664d-4e06-b9c9-76c12e9d16de",
                            "https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/sasageyo_lofi.mp3?alt=media&token=719f0afd-1981-40ab-99ff-c7c4d7cfc795",
                            "Description 1",
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad architecto blanditiis doloremque error, incidunt maiores molestias quaerat quam qui quo reprehenderit, veniam voluptatum! Fuga harum officia ratione repellendus vitae. Aliquam, dignissimos est? Alias amet autem, blanditiis consectetur consequatur cum dolores est eum facere fugit obcaecati praesentium, quas unde ut voluptatem?"
                        )
                    )
                )
            )
        )
    }
}