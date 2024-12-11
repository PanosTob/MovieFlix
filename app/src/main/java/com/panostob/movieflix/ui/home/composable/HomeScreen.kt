package com.panostob.movieflix.ui.home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.panostob.movieflix.ui.home.model.HomeUiState
import com.panostob.movieflix.util.composable.AnimatedNoInternetConnectionItem
import com.panostob.movieflix.util.navigation.NavigationRoute

@Composable
fun HomeScreen(
    uiState: State<HomeUiState>,
    navigateToRoute: (NavigationRoute) -> Unit,
) {
    HomeContent(
        uiState = uiState.value,
        navigateToRoute = navigateToRoute,
    )

    AnimatedNoInternetConnectionItem(
        showInternetDialog = uiState.value.showNoInternetConnectionView,
        onDismissConnectionView = { uiState.value.onDismissConnectionView() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    uiState: HomeUiState,
    navigateToRoute: (NavigationRoute) -> Unit,
) {
    val popularMovies = uiState.popularMoviesFlow.collectAsLazyPagingItems()
    val isLoading = remember(popularMovies.loadState.refresh) { derivedStateOf { popularMovies.loadState.refresh is LoadState.Loading } }

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = isLoading.value,
        onRefresh = uiState.refresh
    ) { }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            popularMovies.loadState.refresh !is LoadState.Loading && popularMovies.itemCount == 0 -> PopularMoviesEmptyContent(
                modifier = Modifier.weight(1f)
            )
            else -> PopularMovieItems(
                onMovieItemClick = { movie ->
                    navigateToRoute(movie.id)
                },
                popularMovies = popularMovies
            )
        }
    }
}