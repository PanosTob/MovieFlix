package com.panostob.movieflix.domain.movies.repository

import com.panostob.movieflix.domain.movies.entity.MovieDetails
import com.panostob.movieflix.domain.movies.entity.MovieReview
import com.panostob.movieflix.domain.movies.entity.PopularMovie
import com.panostob.movieflix.domain.movies.entity.SimilarMovie

interface MoviesRepository {
    suspend fun getPopularMovies(page: Int): List<PopularMovie>

    suspend fun getMovieDetails(movieId: Int): MovieDetails?

    suspend fun getMovieReviews(movieId: Int): List<MovieReview>?

    suspend fun getSimilarMovies(movieId: Int): List<SimilarMovie>?
}