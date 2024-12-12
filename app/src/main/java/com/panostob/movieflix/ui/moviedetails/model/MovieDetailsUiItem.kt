package com.panostob.movieflix.ui.moviedetails.model

import androidx.compose.runtime.MutableState
import com.panostob.movieflix.domain.movies.entity.MovieReview

data class MovieDetailsUiItem (
    val id: Int,
    val title: String,
    val imagePath: String,
    val genres: String,
    val releaseDate: String,
    val runtime: String,
    val description: String,
    val starRating: Float?,
    val isFavorite: MutableState<Boolean>,
    val cast: String,
    val reviews: List<MovieReview>,
    val similarMoviesImagePaths: List<Pair<Int,String>>
)