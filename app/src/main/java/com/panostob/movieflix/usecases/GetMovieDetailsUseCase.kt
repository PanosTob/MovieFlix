package com.panostob.movieflix.usecases

import android.util.Log
import com.panostob.movieflix.domain.movies.entity.MovieDetails
import com.panostob.movieflix.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MoviesRepository,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) {
    suspend operator fun invoke(movieId: Int): MovieDetails? {
        return try {
            val movieDetails = repository.getMovieDetails(movieId)

            getSimilarMoviesUseCase(movieId)?.let { movieDetails?.similarMovies?.addAll(it.take(6)) }
            getMovieReviewsUseCase(movieId)?.let { movieDetails?.reviews?.addAll(it.take(3)) }

            movieDetails
        } catch (ex: Exception) {
            Log.e(GetMovieDetailsUseCase::class.java.toString(), ex.toString())
            null
        }
    }
}