package com.panostob.movieflix.usecases

import android.util.Log
import com.panostob.movieflix.domain.movies.entity.UpdateDatasourceResult
import com.panostob.movieflix.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class SaveFavoriteMovieUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): UpdateDatasourceResult {
        return try {
            repository.setFavoriteMovie(movieId)
            UpdateDatasourceResult.Success
        } catch (ex: Exception) {
            Log.e(SaveFavoriteMovieUseCase::class.java.simpleName, ex.toString())
            UpdateDatasourceResult.Failed
        }
    }
}