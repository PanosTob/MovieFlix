package com.panostob.movieflix.ui.home.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.PagingData
import com.panostob.movieflix.domain.movies.entity.PopularMovie
import com.panostob.movieflix.util.navigation.NavigationRoute
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val popularMoviesFlow: Flow<PagingData<PopularMovieUiItem>>,
    val navigateToRoute: MutableState<NavigationRoute?> = mutableStateOf(null),
    val showNoInternetConnectionView: MutableState<Boolean> = mutableStateOf(false),
    val onMovieClick: (Int) -> Unit = {},
    val onDismissConnectionView: () -> Unit = {},
    val onFavoriteMovieClick: (Int) -> Unit = {},
    val refresh: () -> Unit = {}
)