package com.example.audino.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.audino.model.dao.SavedBooksDao
import com.example.audino.model.response.BookResponse

@Database(entities = [BookResponse::class], version = 1)
abstract class AudinoDatabase : RoomDatabase() {

    abstract fun savedBooksDao() : SavedBooksDao

    companion object {

        @Volatile
        private var INSTANCE: AudinoDatabase? = null

        fun getAudinoDb(context: Context): AudinoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AudinoDatabase::class.java,
                    "audino_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}