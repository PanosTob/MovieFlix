package com.panostob.movieflix.usecases

import android.util.Log
import com.panostob.movieflix.domain.movies.entity.UpdateDatasourceResult
import com.panostob.movieflix.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class DeleteFavoriteMovieUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): UpdateDatasourceResult {
        return try {
            repository.deleteFavoriteMovie(movieId)
            UpdateDatasourceResult.Success
        } catch (ex: Exception) {
            Log.e(DeleteFavoriteMovieUseCase::class.java.simpleName, ex.toString())
            UpdateDatasourceResult.Failed
        }
    }
}