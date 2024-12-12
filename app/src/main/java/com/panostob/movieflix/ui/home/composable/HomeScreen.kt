package com.panostob.movieflix.ui.home.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.panostob.movieflix.BuildConfig
import com.panostob.movieflix.R
import com.panostob.movieflix.ui.home.model.HomeUiState
import com.panostob.movieflix.ui.home.model.PopularMovieUiItem
import com.panostob.movieflix.ui.theme.MovieFlixTheme
import com.panostob.movieflix.util.composable.AnimatedNoInternetConnectionItem
import com.panostob.movieflix.util.navigation.NavigationRoute
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    uiState: State<HomeUiState>
) {
    HomeContent(
        uiState = uiState.value
    )

    AnimatedNoInternetConnectionItem(
        showInternetDialog = uiState.value.showNoInternetConnectionView,
        onDismissConnectionView = { uiState.value.onDismissConnectionView() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    uiState: HomeUiState
) {
    val popularMovies = uiState.popularMoviesFlow.collectAsLazyPagingItems()
    val isLoading = remember(popularMovies.loadState.refresh) { derivedStateOf { popularMovies.loadState.refresh is LoadState.Loading } }

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = isLoading.value,
        onRefresh = uiState.refresh
    ) {
        val showEmptyContent by remember(popularMovies.loadState.refresh, popularMovies.itemCount) {
            derivedStateOf {
                popularMovies.loadState.refresh !is LoadState.Loading && popularMovies.itemCount == 0
            }
        }
        if (showEmptyContent) {
            PopularMoviesEmptyContent(
                modifier = Modifier.wrapContentSize()
            )
        }

        PopularMovieItems(
            onMovieItemClick = {
                uiState.onMovieClick(it)
            },
            popularMovies = popularMovies,
            onFavoriteMovieClick = {
                uiState.onFavoriteMovieClick(it)
            }
        )
    }
}

@Composable
fun PopularMoviesEmptyContent(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.empty_popular_movies_content),
) {
    Text(
        modifier = modifier.wrapContentSize(),
        text = message,
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
fun HomeContentPreview() {
    MovieFlixTheme {
        HomeContent(
            uiState = HomeUiState(
                popularMoviesFlow = flowOf(
                    PagingData.from(
                        listOf(
                            PopularMovieUiItem(
                                id = 912649,
                                title = "Venom Collection",
                                imagePath = "",
                                date = "2024-10-22",
                                starRating = 3.35f,
                            )
                        )
                    )
                )
            )
        )
    }
}