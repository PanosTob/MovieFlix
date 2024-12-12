package com.panostob.movieflix.data.movies.model


data class LocalPopularMovie(
    val id: Int?,
    val title: String?,
    val imagePath: String?,
    val date: String?,
    val starRating: Double?,
    val isFavorite: Boolean
)