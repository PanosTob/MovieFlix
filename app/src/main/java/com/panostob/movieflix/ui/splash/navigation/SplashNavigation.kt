package com.panostob.movieflix.ui.splash.navigation

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
import com.panostob.movieflix.ui.splash.composable.SplashScreen
import com.panostob.movieflix.ui.splash.model.SplashUiState
import com.panostob.movieflix.ui.splash.viewmodel.SplashViewModel
import com.panostob.movieflix.util.navigation.NavigationRoute
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute : NavigationRoute()

fun NavGraphBuilder.splashScreen(
    navigateToRoute: (NavigationRoute) -> Unit
) {
    composable<SplashRoute> {
        val viewModel: SplashViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        SplashScreenSideEffects(
            uiState = uiState,
            navigateToRoute = navigateToRoute
        )

        SplashScreen()
    }
}

@Composable
private fun SplashScreenSideEffects(
    uiState: State<SplashUiState>,
    navigateToRoute: (NavigationRoute) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(key1 = Unit) {
        snapshotFlow { uiState.value.navigateToRoute.value }
            .filterNotNull()
            .flowWithLifecycle(lifecycle)
            .collectLatest { route -> navigateToRoute(route) }
    }
}