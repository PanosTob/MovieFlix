package com.panostob.movieflix.data.movies.datasource.remote

import com.panostob.movieflix.data.movies.model.remote.RemoteMovieDetailsResponse
import com.panostob.movieflix.data.movies.model.remote.RemoteMovieReviewsResponse
import com.panostob.movieflix.data.movies.model.remote.RemotePopularMoviesResponse
import com.panostob.movieflix.data.movies.model.remote.RemoteSimilarMoviesResponse

interface MoviesRemoteDataSource {

    suspend fun getPopularMovies(page: Int): RemotePopularMoviesResponse

    suspend fun getMovieDetails(movieId: Int): RemoteMovieDetailsResponse

    suspend fun getMovieReviews(movieId: Int): RemoteMovieReviewsResponse

    suspend fun getSimilarMovies(movieId: Int): RemoteSimilarMoviesResponse
}