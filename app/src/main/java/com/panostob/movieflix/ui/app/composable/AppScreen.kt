package com.panostob.movieflix.ui.app.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.panostob.movieflix.ui.app.model.AppUiState
import com.panostob.movieflix.ui.app.navigation.AppNavGraphRoute
import com.panostob.movieflix.ui.app.navigation.appNavGraph
import com.panostob.movieflix.ui.theme.MovieFlixTheme

@Composable
fun AppScreen(
    navController: NavHostController,
    uiState: State<AppUiState>
) {
    MovieFlixTheme {
        AppContent(
            navController = navController,
            uiState = uiState.value,
        )
    }
}

@Composable
private fun AppContent(
    navController: NavHostController,
    uiState: AppUiState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        AppNavHost(
            navController = navController
        )
        AppProgressIndicator(
            uiState = uiState.loadingUiState,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = AppNavGraphRoute,
    ) {
        appNavGraph(
            navController = navController
        )
    }
}