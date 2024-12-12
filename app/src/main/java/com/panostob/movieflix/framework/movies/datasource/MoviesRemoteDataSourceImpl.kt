package com.panostob.movieflix.framework.movies.datasource

import com.panostob.movieflix.data.movies.datasource.MoviesRemoteDataSource
import com.panostob.movieflix.data.movies.model.RemoteMovieDetailsResponse
import com.panostob.movieflix.data.movies.model.RemoteMovieReviewsResponse
import com.panostob.movieflix.data.movies.model.RemotePopularMoviesResponse
import com.panostob.movieflix.data.movies.model.RemoteSimilarMoviesResponse
import com.panostob.movieflix.framework.movies.MoviesApi
import com.panostob.movieflix.util.ext.requireNotNull
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    val moviesApi: MoviesApi,
//    val movieDatabase: MoviesDatabase
) : MoviesRemoteDataSource {

    override suspend fun getPopularMovies(page: Int): RemotePopularMoviesResponse {
        return moviesApi.getPopularMovies(page).requireNotNull()
    }

    override suspend fun getMovieDetails(movieId: Int): RemoteMovieDetailsResponse {
        return moviesApi.getMovieDetails(movieId).requireNotNull()
    }

    override suspend fun getMovieReviews(movieId: Int): RemoteMovieReviewsResponse {
        return moviesApi.getMovieReviews(movieId).requireNotNull()
    }

    override suspend fun getSimilarMovies(movieId: Int): RemoteSimilarMoviesResponse {
        return moviesApi.getSimilarMovies(movieId).requireNotNull()
    }

    override fun setFavoriteMovie(movieId: Int) {
//        movieDao.setFavoriteMovie(movieId)
    }
}