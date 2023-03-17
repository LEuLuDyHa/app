package com.github.leuludyha.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Edition
import com.github.leuludyha.domain.model.Work

@Database(
    entities = [WorkEntity::class, AuthorEntity::class, WorkAuthorCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class LibraryDB : RoomDatabase() {
    abstract fun libraryDao(): LibraryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LibraryDB? = null

        fun getDatabase(context: Context): LibraryDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LibraryDB::class.java,
                    "library_databse"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}