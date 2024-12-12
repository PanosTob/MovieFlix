package com.panostob.movieflix.data.movies.datasource

import com.panostob.movieflix.data.movies.model.RemoteMovieDetailsResponse
import com.panostob.movieflix.data.movies.model.RemoteMovieReviewsResponse
import com.panostob.movieflix.data.movies.model.RemotePopularMoviesResponse
import com.panostob.movieflix.data.movies.model.RemoteSimilarMoviesResponse

interface MoviesRemoteDataSource {

    suspend fun getPopularMovies(page: Int): RemotePopularMoviesResponse

    suspend fun getMovieDetails(movieId: Int): RemoteMovieDetailsResponse

    suspend fun getMovieReviews(movieId: Int): RemoteMovieReviewsResponse

    suspend fun getSimilarMovies(movieId: Int): RemoteSimilarMoviesResponse

    fun setFavoriteMovie(movieId: Int)
}