package com.panostob.movieflix.ui.home.mapper

import androidx.compose.runtime.mutableStateOf
import com.panostob.movieflix.domain.movies.entity.PopularMovie
import com.panostob.movieflix.ui.home.model.PopularMovieUiItem
import javax.inject.Inject

class PopularMoviesUiMapper @Inject constructor() {
    operator fun invoke(movies: List<PopularMovie>): List<PopularMovieUiItem> {
        return movies.map {
            PopularMovieUiItem(
                id = it.id,
                title = it.title,
                imagePath = it.imagePath,
                date = it.date,
                starRating = it.starRating?.toFloat(),
                isFavorite = mutableStateOf(it.isFavorite)
            )
        }
    }
}