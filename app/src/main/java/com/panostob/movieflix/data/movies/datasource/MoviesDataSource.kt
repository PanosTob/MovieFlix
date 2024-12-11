package com.panostob.movieflix.data.movies.datasource

import com.panostob.movieflix.data.movies.model.RemoteMovieDetailsResponse
import com.panostob.movieflix.data.movies.model.RemoteMovieReviewsResponse
import com.panostob.movieflix.data.movies.model.RemotePopularMoviesResponse
import com.panostob.movieflix.data.movies.model.RemoteSimilarMoviesResponse
import com.panostob.movieflix.domain.movies.entity.MovieReview
import com.panostob.movieflix.domain.movies.entity.SimilarMovie

interface MoviesDataSource {

    suspend fun getPopularMovies(page: Int): RemotePopularMoviesResponse

    suspend fun getMovieDetails(movieId: Int): RemoteMovieDetailsResponse

    suspend fun getMovieReviews(movieId: Int): RemoteMovieReviewsResponse

    suspend fun getSimilarMovies(movieId: Int): RemoteSimilarMoviesResponse
}