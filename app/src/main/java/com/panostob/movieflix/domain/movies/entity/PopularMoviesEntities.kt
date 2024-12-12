package com.panostob.movieflix.domain.movies.entity

data class PopularMovie(
    val id: Int,
    val title: String,
    val imagePath: String,
    val date: String,
    val starRating: Double?,
    val isFavorite: Boolean
)