package com.panostob.movieflix.framework.movies.datasource.remote

import com.panostob.movieflix.data.movies.datasource.remote.MoviesRemoteDataSource
import com.panostob.movieflix.data.movies.model.remote.RemoteMovieDetailsResponse
import com.panostob.movieflix.data.movies.model.remote.RemoteMovieReviewsResponse
import com.panostob.movieflix.data.movies.model.remote.RemotePopularMoviesResponse
import com.panostob.movieflix.data.movies.model.remote.RemoteSimilarMoviesResponse
import com.panostob.movieflix.framework.movies.MoviesApi
import com.panostob.movieflix.util.ext.requireNotNull
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    val moviesApi: MoviesApi
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
}