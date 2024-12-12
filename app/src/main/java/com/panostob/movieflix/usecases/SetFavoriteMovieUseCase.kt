package com.panostob.movieflix.usecases

import com.panostob.movieflix.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class SetFavoriteMovieUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(movieId: Int) {
        repository.setFavoriteMovie(movieId)
    }
}