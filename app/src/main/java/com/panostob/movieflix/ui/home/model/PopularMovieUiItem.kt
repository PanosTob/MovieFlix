package com.panostob.movieflix.ui.home.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class PopularMovieUiItem(
    val id: Int,
    val title: String,
    val imagePath: String,
    val date: String,
    val starRating: Float?,
    val isFavorite: MutableState<Boolean> = mutableStateOf(false),
)