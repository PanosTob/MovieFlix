package com.panostob.movieflix.ui.moviedetails.model

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.panostob.movieflix.util.navigation.NavigationRoute

sealed class MovieDetailsUiState(
    val showNoInternetConnectionView: MutableState<Boolean> = mutableStateOf(false)
) {
    data object Loading : MovieDetailsUiState()
    data object Error : MovieDetailsUiState()
    data class Success(
        val movieDetailsUiItem: MovieDetailsUiItem,
        val onFavoriteMovieClick: (MovieDetailsUiItem) -> Unit = {},
        val onSimilarMovieClick: (Int) -> Unit = {},
        val navigateToRoute: MutableState<NavigationRoute?> = mutableStateOf(null)
    ) : MovieDetailsUiState()
}