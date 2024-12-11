package com.panostob.movieflix.usecases

import android.util.Log
import com.panostob.movieflix.domain.movies.entity.MovieDetails
import com.panostob.movieflix.domain.movies.entity.MovieReview
import com.panostob.movieflix.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): List<MovieReview>? {
        return try {
            repository.getMovieReviews(movieId)
        } catch (ex: Exception) {
            Log.e(GetMovieReviewsUseCase::class.java.toString(), ex.toString())
            null
        }
    }
}