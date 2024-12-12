package com.panostob.movieflix.ui.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.panostob.movieflix.ui.home.composable.HomeScreen
import com.panostob.movieflix.ui.home.model.HomeUiState
import com.panostob.movieflix.ui.home.viewmodel.HomeViewModel
import com.panostob.movieflix.util.navigation.NavigationRoute
import com.panostob.movieflix.util.network.connection.rememberConnectivityMonitor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : NavigationRoute()

fun NavGraphBuilder.homeScreen(
    navigateToRoute: (NavigationRoute) -> Unit,
) {
    composable<HomeRoute> {
        val viewModel: HomeViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        rememberConnectivityMonitor(
            onConnected = { viewModel.onNetworkConnected() },
            onDisconnected = { viewModel.onNetworkDisconnected() }
        )

        HomeScreenSideEffects(
            uiState = uiState,
            navigateToRoute = { route ->
                navigateToRoute(route)
                viewModel.resetRouteNavigation()
            }
        )

        HomeScreen(
            uiState = uiState
        )
    }
}

@Composable
private fun HomeScreenSideEffects(
    uiState: State<HomeUiState>,
    navigateToRoute: (NavigationRoute) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { uiState.value.navigateToRoute.value }
            .filterNotNull()
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { route -> navigateToRoute(route) }
    }
}