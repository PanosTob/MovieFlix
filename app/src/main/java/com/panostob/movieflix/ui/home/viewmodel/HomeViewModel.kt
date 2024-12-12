package com.panostob.movieflix.ui.home.viewmodel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.panostob.movieflix.ui.base.BaseViewModel
import com.panostob.movieflix.ui.home.mapper.PopularMoviesUiMapper
import com.panostob.movieflix.ui.home.model.HomeUiState
import com.panostob.movieflix.ui.home.paging.PopularMoviesPagingSource
import com.panostob.movieflix.ui.moviedetails.navigation.MovieDetailsRoute
import com.panostob.movieflix.usecases.GetPopularMoviesUseCase
import com.panostob.movieflix.usecases.SetFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val setFavoriteMovieUseCase: SetFavoriteMovieUseCase,
    private val popularMoviesUiMapper: PopularMoviesUiMapper
) : BaseViewModel() {

    private var job: Job? = null

    private val _uiState = MutableStateFlow(
        HomeUiState(
            popularMoviesFlow = emptyFlow(),
            onDismissConnectionView = { onDismissConnectionView() },
            onFavoriteMovieClick = { onFavoriteMovieClick(it) },
            onMovieClick = { navigateToRoute(it) },
            refresh = ::setupPopularMovies
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        launch {
            setupPopularMovies()
        }
    }

    private fun setupPopularMovies() {
        job?.cancel()
        job = launch {
            val pagerFlow = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                PopularMoviesPagingSource(
                    getPopularMoviesPagingData = { key -> getPopularMoviesUseCase(page = key) },
                    mapPopularMoviesToUiItems = { movies -> popularMoviesUiMapper(movies) }
                )
            }.flow

            _uiState.update { it.copy(popularMoviesFlow = pagerFlow) }
        }
    }

    private fun onDismissConnectionView() {
        _uiState.value.showNoInternetConnectionView.value = false
    }

    fun navigateToRoute(movieId: Int) {
        _uiState.value.navigateToRoute.value = MovieDetailsRoute(movieId)
    }

    fun resetRouteNavigation() {
        _uiState.value.navigateToRoute.value = null
    }

    private fun onFavoriteMovieClick(movieId: Int) {
        setFavoriteMovieUseCase(movieId)
    }

    fun onNetworkConnected() {
        _uiState.value.showNoInternetConnectionView.value = false
    }

    fun onNetworkDisconnected() {
        _uiState.value.showNoInternetConnectionView.value = true
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}