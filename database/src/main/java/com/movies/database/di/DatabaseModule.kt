package com.movies.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.movies.database.db.MoviesDatabase
import com.movies.database.db.movie.MovieDao
import com.movies.database.repository.RoomDBRepository

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideMovieDao(@ApplicationContext context: Context): MovieDao {
        return MoviesDatabase.getInstance(context).movieDao
    }

    @Provides
    fun provideRoomDBRepository(
        movieDao: MovieDao
    ) = RoomDBRepository(
        movieDao = movieDao
    )
}
