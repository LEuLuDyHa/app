package com.github.leuludyha.ibdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ActivityItem::class], version = 1, exportSchema = false)
abstract class ActivityDatabase : RoomDatabase() {

    abstract fun activityDao(): ActivityDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ActivityDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ActivityDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ActivityDatabase::class.java,
                    "activity_database"
                ).addCallback(ActivityDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class ActivityDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.activityDao())
                }
            }
        }

        suspend fun populateDatabase(activityDao: ActivityDao) {
            // Delete all content here.
            activityDao.deleteAll()

/*          // Add sample words.
            var word = Activity("Hello")
            activityDao.insert(word)
            word = Activity("World!")
            activityDao.insert(word)*/
        }
    }
}