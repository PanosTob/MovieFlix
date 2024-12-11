package com.panostob.movieflix.usecases

import android.util.Log
import com.panostob.movieflix.domain.movies.entity.SimilarMovie
import com.panostob.movieflix.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): List<SimilarMovie>? {
        return try {
            repository.getSimilarMovies(movieId)
        } catch (ex: Exception) {
            Log.e(GetSimilarMoviesUseCase::class.java.toString(), ex.toString())
            null
        }
    }
}