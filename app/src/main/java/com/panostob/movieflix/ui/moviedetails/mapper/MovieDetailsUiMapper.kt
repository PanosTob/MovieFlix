package com.panostob.movieflix.ui.moviedetails.mapper

import androidx.compose.runtime.mutableStateOf
import com.panostob.movieflix.domain.movies.entity.MovieDetails
import com.panostob.movieflix.ui.moviedetails.model.MovieDetailsUiItem
import javax.inject.Inject

class MovieDetailsUiMapper @Inject constructor() {
    operator fun invoke(movie: MovieDetails?): MovieDetailsUiItem? {
        if (movie == null) return null
        return MovieDetailsUiItem(
            id = movie.id,
            title = movie.title,
            imagePath = movie.imagePath,
            genres = movie.genres.joinToString(", ") { it.name },
            releaseDate = movie.releaseDate,
            runtime = "${movie.runtime?.div(60)}h ${movie.runtime?.mod(60)}min",
            description = movie.description,
            starRating = movie.starRating?.toFloat(),
            cast = movie.cast.joinToString(", ") { it.name },
            reviews = movie.reviews,
            similarMoviesImagePaths = movie.similarMovies.map { Pair(it.id, it.imagePath) },
            isFavorite = mutableStateOf(movie.isFavorite)
        )
    }
}