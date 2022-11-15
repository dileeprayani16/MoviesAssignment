package com.movies.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movies.database.db.movie.Movie
import com.movies.database.db.movie.MovieDao

@Database(
    entities = [Movie::class],
    version = 2,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    companion object {

        @Volatile
        private var instance: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {
            synchronized(this) {
                var mInstance = instance

                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDatabase::class.java,
                        "movies_database"
                    ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    instance = mInstance
                }
                return mInstance
            }
        }
    }
}
