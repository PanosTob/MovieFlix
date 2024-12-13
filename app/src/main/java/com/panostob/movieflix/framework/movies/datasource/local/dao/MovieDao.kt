package com.panostob.movieflix.framework.movies.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.panostob.movieflix.data.movies.model.local.LocalFavoriteMovie

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM favorite_movies")
    suspend fun getAllFavoriteMovies(): List<LocalFavoriteMovie>

    @Transaction
    @Query("SELECT * FROM favorite_movies WHERE movie_id = :movieId LIMIT 1")
    suspend fun getFavoriteMovieById(movieId: Int): LocalFavoriteMovie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(localFavoriteMovie: LocalFavoriteMovie)

    @Query("DELETE FROM favorite_movies WHERE movie_id = :movieId")
    suspend fun deleteFavoriteMovie(vararg movieId: Int)
}