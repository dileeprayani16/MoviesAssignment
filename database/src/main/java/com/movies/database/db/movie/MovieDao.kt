package com.movies.database.db.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>)

    @Query("SELECT * FROM movies ORDER BY title ASC")
    fun getAll(): MutableList<Movie>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun get(id: Long): Movie

    @Query("DELETE FROM movies")
    fun deleteRecords()
}