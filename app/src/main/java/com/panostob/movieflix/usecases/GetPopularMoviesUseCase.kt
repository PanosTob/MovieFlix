package com.panostob.movieflix.usecases

import android.util.Log
import com.panostob.movieflix.domain.movies.entity.PopularMovie
import com.panostob.movieflix.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(page: Int): List<PopularMovie> {
        return try {
            repository.getPopularMovies(page)
        } catch (ex: Exception) {
            Log.e(GetPopularMoviesUseCase::class.java.toString(), ex.toString())
            emptyList()
        }
    }
}