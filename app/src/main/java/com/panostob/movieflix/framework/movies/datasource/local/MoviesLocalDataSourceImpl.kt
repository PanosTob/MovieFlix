package com.panostob.movieflix.framework.movies.datasource.local

import com.panostob.movieflix.data.movies.datasource.local.MoviesLocalDataSource
import com.panostob.movieflix.data.movies.model.local.LocalFavoriteMovie
import com.panostob.movieflix.framework.movies.datasource.local.dao.MovieDao
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor(
    private val dao: MovieDao
): MoviesLocalDataSource {
    override suspend fun getAllFavoriteMovies(): List<LocalFavoriteMovie> {
        return dao.getAllFavoriteMovies()
    }

    override suspend fun getFavoriteMovieById(movieId: Int): LocalFavoriteMovie? {
        return dao.getFavoriteMovieById(movieId)
    }

    override suspend fun saveFavoriteMovie(localFavoriteMovie: LocalFavoriteMovie) {
        return dao.insertFavoriteMovie(localFavoriteMovie)
    }

    override suspend fun deleteFavoriteMovie(movieId: Int) {
        return dao.deleteFavoriteMovie(movieId)
    }
}