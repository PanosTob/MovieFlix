package com.panostob.movieflix.ui.moviedetails.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.panostob.movieflix.ui.moviedetails.composable.MovieDetailsScreen
import com.panostob.movieflix.ui.moviedetails.viewmodel.MovieDetailsViewModel
import com.panostob.movieflix.util.composable.NoInternetConnectionItem
import com.panostob.movieflix.util.ext.slideInOutComposable
import com.panostob.movieflix.util.navigation.NavigationRoute
import com.panostob.movieflix.util.network.connection.rememberConnectivityMonitor
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(val movieId: Int) : NavigationRoute()

fun NavGraphBuilder.movieDetailsScreen(navigateToRoute: (NavigationRoute) -> Unit) {
    slideInOutComposable<MovieDetailsRoute> {
        val viewModel: MovieDetailsViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        rememberConnectivityMonitor(
            onConnected = { viewModel.onNetworkConnected() },
            onDisconnected = { viewModel.onNetworkDisconnected() }
        )

        MovieDetailsScreen(
            uiState = uiState,
            navigateToRoute = {
                navigateToRoute(it)
                viewModel.resetRouteNavigation()
            }
        )
    }
}