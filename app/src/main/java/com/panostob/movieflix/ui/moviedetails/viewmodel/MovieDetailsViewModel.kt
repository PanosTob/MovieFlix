package com.panostob.movieflix.ui.moviedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.panostob.movieflix.domain.movies.entity.UpdateDatasourceResult
import com.panostob.movieflix.ui.base.BaseViewModel
import com.panostob.movieflix.ui.home.model.PopularMovieUiItem
import com.panostob.movieflix.ui.moviedetails.mapper.MovieDetailsUiMapper
import com.panostob.movieflix.ui.moviedetails.model.MovieDetailsUiItem
import com.panostob.movieflix.ui.moviedetails.model.MovieDetailsUiState
import com.panostob.movieflix.ui.moviedetails.navigation.MovieDetailsRoute
import com.panostob.movieflix.usecases.DeleteFavoriteMovieUseCase
import com.panostob.movieflix.usecases.GetMovieDetailsUseCase
import com.panostob.movieflix.usecases.SaveFavoriteMovieUseCase
import com.panostob.movieflix.util.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val movieDetailsUiMapper: MovieDetailsUiMapper,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val args = savedStateHandle.toRoute<MovieDetailsRoute>()

    private val _uiState: MutableStateFlow<MovieDetailsUiState> = MutableStateFlow(MovieDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        launch {
            setupMovieDetails(args.movieId)
        }
    }

    private fun setupMovieDetails(movieId: Int) {
        launch {
            val movieDetails = movieDetailsUiMapper(getMovieDetailsUseCase(movieId))
            if (movieDetails == null) {
                _uiState.update { MovieDetailsUiState.Error }
                return@launch
            }
            _uiState.update {
                MovieDetailsUiState.Success(
                    movieDetailsUiItem = movieDetails,
                    onFavoriteMovieClick = { onFavoriteMovieClick(it) },
                    onSimilarMovieClick = { onSimilarMovieClick(it) }
                )
            }
        }
    }

    private fun onFavoriteMovieClick(movie: MovieDetailsUiItem) {
        launch {
            val result = if (!movie.isFavorite.value) saveFavoriteMovieUseCase(movie.id) else deleteFavoriteMovieUseCase(movie.id)
            if (result is UpdateDatasourceResult.Success) {
                movie.isFavorite.value = !movie.isFavorite.value
            }
        }
    }

    fun navigateToRoute(route: NavigationRoute) {
        if (_uiState.value is MovieDetailsUiState.Success) {
            (_uiState.value as MovieDetailsUiState.Success).navigateToRoute.value = route
        }
    }

    fun resetRouteNavigation() {
        if (_uiState.value is MovieDetailsUiState.Success) {
            (_uiState.value as MovieDetailsUiState.Success).navigateToRoute.value = null
        }
    }

    private fun onSimilarMovieClick(movieId: Int) {
        setupMovieDetails(movieId)
    }

    fun onNetworkConnected() {
        _uiState.value.showNoInternetConnectionView.value = false
    }

    fun onNetworkDisconnected() {
        _uiState.value.showNoInternetConnectionView.value = true
    }

}