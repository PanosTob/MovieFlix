package com.panostob.movieflix.ui.home.viewmodel

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.panostob.movieflix.domain.movies.entity.PopularMovie
import com.panostob.movieflix.domain.movies.entity.UpdateDatasourceResult
import com.panostob.movieflix.ui.base.BaseViewModel
import com.panostob.movieflix.ui.home.mapper.PopularMoviesUiMapper
import com.panostob.movieflix.ui.home.model.HomeUiState
import com.panostob.movieflix.ui.home.model.PopularMovieUiItem
import com.panostob.movieflix.ui.home.paging.PopularMoviesPagingSource
import com.panostob.movieflix.ui.moviedetails.navigation.MovieDetailsRoute
import com.panostob.movieflix.usecases.DeleteFavoriteMovieUseCase
import com.panostob.movieflix.usecases.GetPopularMoviesUseCase
import com.panostob.movieflix.usecases.SaveFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import java.nio.file.Files.delete
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val popularMoviesUiMapper: PopularMoviesUiMapper
) : BaseViewModel() {

    private var job: Job? = null

    private val _uiState = MutableStateFlow(
        HomeUiState(
            popularMoviesFlow = emptyFlow(),
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

    private fun onFavoriteMovieClick(movie: PopularMovieUiItem) {
        launch {
            val result = if (!movie.isFavorite.value) saveFavoriteMovieUseCase(movie.id) else deleteFavoriteMovieUseCase(movie.id)
            if (result is UpdateDatasourceResult.Success) {
                movie.isFavorite.value = !movie.isFavorite.value
            }
        }
    }

    fun onNetworkConnected() {
        _uiState.value.showNoInternetConnectionView.value = false
        setupPopularMovies()
    }

    fun onNetworkDisconnected() {
        _uiState.value.showNoInternetConnectionView.value = true
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}