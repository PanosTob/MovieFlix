package com.panostob.movieflix.data.movies.datasource.local

import com.panostob.movieflix.data.movies.model.local.LocalFavoriteMovie

interface MoviesLocalDataSource {

    suspend fun getAllFavoriteMovies(): List<LocalFavoriteMovie>

    suspend fun getFavoriteMovieById(movieId: Int): LocalFavoriteMovie?

    suspend fun saveFavoriteMovie(localFavoriteMovie: LocalFavoriteMovie)

    suspend fun deleteFavoriteMovie(movieId: Int)
}